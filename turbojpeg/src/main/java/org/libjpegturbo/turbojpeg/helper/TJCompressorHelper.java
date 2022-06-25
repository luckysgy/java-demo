package org.libjpegturbo.turbojpeg.helper;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.io.IOUtils;
import org.libjpegturbo.turbojpeg.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

/**
 * turbojpeg helper: 用于操作压缩图片
 * This program demonstrates how to compress, decompress, and transform JPEG
 * images using the TurboJPEG Java API
 * @author shenguangyang
 * @date 2022-01-15 14:25
 */
public class TJCompressorHelper implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(TJCompressorHelper.class);
    private int outSubSamp = -1;

    static final TJScalingFactor[] SCALING_FACTORS = TJ.getScalingFactors();
    /**
     * 输入输出图片格式
     */
    private static final String IN_FORMAT = "jpg";
    private static final String OUT_FORMAT = "jpg";

    private static final String CLASS_NAME = TJCompressorHelper.class.getName();

    private static final int DEFAULT_SUBSAMP = TJ.SAMP_444;
    private static final int DEFAULT_QUALITY = 70;


    private static final String[] SUBSAMP_NAME = {
            "4:4:4", "4:2:2", "4:2:0", "Grayscale", "4:4:0", "4:1:1"
    };

    private static final String[] COLORSPACE_NAME = {
            "RGB", "YCbCr", "GRAY", "CMYK", "YCCK"
    };

    private String imageInFormat;
    private String imageOutFormat;

    @Override
    public void close() throws Exception {

    }

    public TJCompressorHelper(String imageInFormat, String imageOutFormat) throws Exception {
        this.imageInFormat = imageInFormat;
        this.imageOutFormat = imageOutFormat;
    }

    /**
     * 压缩图片, 只对jpg格式进行压缩处理, 其余格式的文件直接返回
     * @param options eg: {@link TJTransform#OPT_CROP}, 以OPT开头的都是改属性的可选值
     * @param op eg: {@link TJTransform#OP_HFLIP}, 以OP开头的都是改属性的可选值
     * @param flags eg: {@link TJ#FLAG_FASTDCT}, {@link TJ#FLAG_FASTUPSAMPLE}, {@link TJ#FLAG_ACCURATEDCT}
     *             以FLAG开头的都是改属性的可选值
     * @param outSubsamp eg: {@link TJ#SAMP_GRAY} and {@link TJ#SAMP_444} and {@link TJ#SAMP_422} and
     *                   {@link TJ#SAMP_420}, 以SAM开头的都是改属性的可选值
     * @param outQual eg: <1-100> , Compress the output image with this JPEG quality level
     * @param jpegBytes 目标图片字节数组
     * @return 压缩之后的图片
     */
    public byte[] compressor(int options, int op, int flags, int outSubsamp, int outQual, int width, int height, byte[] jpegBytes) throws TJException {
        if (ObjectUtil.isNull(jpegBytes)) {
            return null;
        }
        if (jpegBytes.length < 1) {
            log.warn("Input file contains no data");
            return null;
        }

        TJScalingFactor scalingFactor = new TJScalingFactor(1, 1);
        BufferedImage img = null;
        byte[] imgBuf = null;

        TJTransform tjTransform = new TJTransform();
        flags |= flags;

        tjTransform.options |= options;
        tjTransform.op = op;
        tjTransform.width = width;
        tjTransform.height = height;

        try {
            if (IN_FORMAT.equalsIgnoreCase(imageInFormat)) {
                /* Input image is a JPEG image.  Decompress and/or transform it. */
                boolean doTransform = (tjTransform.op != TJTransform.OP_NONE ||
                        tjTransform.options != 0 || tjTransform.cf != null);

                TJDecompressor tjd;
                if (doTransform) {
                    /* Transform it. */
                    TJTransformer tjt = new TJTransformer(jpegBytes);
                    TJTransform[] xforms = new TJTransform[1];
                    xforms[0] = tjTransform;
                    xforms[0].options |= TJTransform.OPT_TRIM;
                    TJDecompressor[] tjds = tjt.transform(xforms, 0);
                    tjd = tjds[0];
                    tjt.close();
                } else
                    tjd = new TJDecompressor(jpegBytes);

                int inSubsamp = tjd.getSubsamp();
                int inColorspace = tjd.getColorspace();

                System.out.println((doTransform ? "Transformed" : "Input") +
                        " Image (jpg):  " + width + " x " + height +
                        " pixels, " + SUBSAMP_NAME[inSubsamp] +
                        " subsampling, " + COLORSPACE_NAME[inColorspace]);
                if (doTransform &&
                        scalingFactor.isOne() && outSubsamp < 0 && outQual < 0) {
                    /* Input image has been transformed, and no re-compression options
                        have been selected.  Write the transformed image to disk and
                        exit. */
                    return tjd.getJPEGBuf();
                }

                if (!OUT_FORMAT.equalsIgnoreCase(imageOutFormat))
                    img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB,
                            flags);
                else
                    imgBuf = tjd.decompress(width, 0, height, TJ.PF_BGRX, flags);
                tjd.close();
            } else {
                /* Input image is not a JPEG image.  Load it into memory. */
            }


            /* Output image format is JPEG.  Compress the uncompressed image. */
            if (outQual < 0)
                outQual = DEFAULT_QUALITY;
            System.out.println(", " + SUBSAMP_NAME[outSubsamp] +
                    " subsampling, quality = " + outQual);

            TJCompressor tjc = new TJCompressor();
            tjc.setSubsamp(outSubsamp);
            tjc.setJPEGQuality(outQual);
            if (img != null)
                tjc.setSourceImage(img, 0, 0, 0, 0);
            else
                tjc.setSourceImage(imgBuf, 0, 0, width, 0, height, TJ.PF_BGRX);
            byte[] jpegBuf = tjc.compress(flags);
            tjc.close();

            return jpegBuf;
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    public byte[] test(byte[] bytes) throws TJException {
        TJCompressor tjc = new TJCompressor();
        tjc.setSubsamp(TJ.SAMP_444);
        tjc.setJPEGQuality(DEFAULT_QUALITY);
        tjc.setSourceImage(bytes, 0, 0, 1920, 0, 1080, TJ.PF_RGB);
        byte[] jpegBuf = tjc.compress(TJ.FLAG_FASTDCT);
        tjc.close();
        return jpegBuf;
    }

    public static void main(String[] args) throws Exception {
        TJCompressorHelper tjCompressorHelper = new TJCompressorHelper("jpg", "jpg");
        byte[] bytes = IOUtils.toByteArray(new FileInputStream("/mnt/images/0.jpg"));
        tjCompressorHelper.test(bytes);
    }

}

<template>
  <div>
    <a-button @click="play">启动播放</a-button>
    <a-button @click="captureImage">截取图片</a-button>
    <br><br>
	  <video id="videoElement" 
      class="video-js vjs-big-play-centered vjs-fluid"
      controls 
      autoplay 
      muted 
      width="1920px" 
      height="1080px"> 
    </video>

    <br>截图<br>
    <canvas class="video-js vjs-big-play-centered vjs-fluid" id="myCanvas" width="1920" height="1080"></canvas>
  </div>
</template>

<script>

import flvjs from 'flv.js'
import video from 'video.js'
import 'video.js/dist/video-js.css'

export default {
  components: {
    video
  },
  data () {
    return {
      path:"ws://192.168.190.70:55002/api/video/play",
      flvPlayer: null,
      streamUrl: "1",
      socket:"",
      targetCanvas: new Map()
    }
  },
   mounted() {
      // 初始化
      this.init()
      let self = this
      this.imageToCanvas("http://192.168.190.70:39000/my-test/1591_664_1891_1079_2336.jpg", (canvas) => {
        var ctx = canvas.getContext('2d')
        self.targetCanvas.set("1", ctx)
      })
    },
    methods:{
      init: function () {
        if(typeof(WebSocket) === "undefined"){
            alert("您的浏览器不支持socket")
        } else {
            // 实例化socket
            this.socket = new WebSocket(this.path)
            // 监听socket连接
            this.socket.onopen = this.open
            // 监听socket错误信息
            this.socket.onerror = this.error
            // 监听socket消息
            this.socket.onmessage = this.getMessage
        }
        let video = document.querySelector("video");
        video.addEventListener('timeupdate', this.captureImage);
      },
      //截取当前帧的图片
      captureImage(){
          var frame = document.querySelector("video");
          let canvas = document.getElementById('myCanvas')
          var ctx = canvas.getContext('2d');      
          ctx.drawImage(frame, 0, 0);
          // var oGrayImg = canvas.toDataURL('image/jpeg');
          // this.imgSrc = oGrayImg

          let imageData = ctx.getImageData(1591, 664, 1891 - 1591, 1079 - 664)
          
          // 图片灰度化
          // let grayImageInfo = this.grayImage(imageData)
          // console.time(1)
          // 特征指纹
          let image1 = this.hashFingerprint(imageData)
          // console.timeEnd(1) 
          
          // console.time(2)
          let ctx2 = this.targetCanvas.get("1")
          let imageData2 = ctx2.getImageData(0, 0, 1891 - 1591, 1079 - 664)
          // 图片灰度化
          // let grayImageInfo2 = this.grayImage(imageData2);
          // 特征指纹
          let image2 = this.hashFingerprint(imageData2);
          // console.timeEnd(2)
        
          let hm = this.getHm(image1, image2);
          let si = this.getSimilarity(image1.length, hm);
          if (si >= 73) {
            console.log("si = " + si);  
          }
          
           
      },

      imageToCanvas(src, cb){
        var canvas = document.createElement('canvas')
        var ctx = canvas.getContext('2d')
        var img = new Image()
        img.src = src
        img.crossOrigin = ''
        img.onload = function (){
          canvas.width = img.width
          canvas.height = img.height
          ctx.drawImage(img, 0, 0, img.width, img.height)
          cb(canvas)
        };
      },

      // 特征指纹
      hashFingerprint(imageData) {
        const grayList = imageData.data.reduce((pre, cur, index) => {
            if ((index + 1) % 4 === 0) {
                pre.push(imageData.data[index - 1])
            }
            return pre
        }, [])
        const length = grayList.length
        const grayAverage = grayList.reduce((pre, next) => (pre + next), 0) / length
        return grayList.map(gray => (gray >= grayAverage ? 1 : 0)).join('')
      },

      //灰度化
      grayImage(imageData) {
        let data = imageData.data;
        let len = imageData.data.length;
        let newData = new Array(len);
        for (let i = 0; i < len; i += 4) {
            const R = data[i];
            const G = data[i + 1];
            const B = data[i + 2];
            const grey = this.getGrayFromRGB(R, G, B);
            newData[i] = grey;
            newData[i + 1] = grey;
            newData[i + 2] = grey;
            newData[i + 3] = 255;
        }
        return this.createImageData(newData);
      },

      //汉明距离
      getHm(image1, image2){
        let distance = 0;
        let len = image1.length;
        for(let i=0; i < len; i++){
            if(image1[i] != image2[i]){
              distance++;
            }
        }
        return distance
      },

      //根据rgb值算出灰度值
      getGrayFromRGB(R, G, B) {
          let a = Math.pow(R, 2.2) + Math.pow(1.5 * G, 2.2) + Math.pow(0.6 * B, 2.2);
          let b = 1 + Math.pow(1.5, 2.2) + Math.pow(0.6, 2.2);
          return parseInt(Math.pow(a / b, 1 / 2.2))
      },

      //相似度
      getSimilarity(strLen,hm){
        return parseInt((strLen - hm)/strLen*100)
      },

      //根据rgba数组生成 imageData 和dataUrl
      createImageData(data) {
        const canvas = document.createElement('canvas')
        canvas.width = 50
        canvas.height = 50
        const ctx = canvas.getContext('2d')
        const imgWidth = Math.sqrt(data.length / 4)
        const newImageData = ctx.createImageData(imgWidth, imgWidth)
        for (let i = 0; i < data.length; i += 4) {
            newImageData.data[i] = data[i]
            newImageData.data[i + 1] = data[i + 1]
            newImageData.data[i + 2] = data[i + 2]
            newImageData.data[i + 3] = data[i + 3]
        }
        ctx.putImageData(newImageData, 0, 0);
        return {
            dataUrl: canvas.toDataURL(),
            imageData: newImageData
        }
      },
      // readFile(file) {
      //   return new Promise((resolve) => {
      //       const reader = new FileReader();
      //       reader.readAsDataURL(file);
      //       reader.addEventListener("load", function () {
      //           resolve(reader.result);
      //       }, false)
      //   })
      // },
      open: function () {
        console.log("socket连接成功")
      },
      error: function () {
          console.log("连接错误")
      },
      getMessage: function (msg) {
        let result = JSON.parse(msg.data)
        console.log(msg.data,"\t", result.data)
        if (result.code == 200 && result.data.dataType == "streamUrl") {
            if (flvjs.isSupported()) {
              var videoElement = document.getElementById('videoElement');
              this.flvPlayer = flvjs.createPlayer({
                type: 'flv',
                isLive: true,
                hasAudio: false,
                // url: result.data.streamUrl
                url: "http://192.168.190.70:39080/live?port=31935&app=video&stream=2"
              });
              this.flvPlayer.attachMediaElement(videoElement);
              this.flvPlayer.load();
              this.flvPlayer.play();
            }
            this.flvPlayer.play();
        }
    },
    play: function () {
      console.log("启动播放")
      let data = {
        // 242265960521733  242266824327173  242267202174981
        "userVideoId": "253965233082437",
        "fastTime": 0        
      }
      this.socket.send(JSON.stringify(data))
    },
    close: function () {
        console.log("socket已经关闭")
    }
  },
  unmounted() {
    this.flvPlayer.destroy();
  }
}
</script>

<style lang="less">
.video-js{
  width: 120%!important;
  height: 100%!important;
}
.video-js .vjs-big-play-button {
  font-size: 3em;
  line-height: 42px!important;
  height: 50px!important;
  width: 50px!important;
  display: block;
  position: absolute!important;
  left:50%!important;
  top:50%!important;
  margin-top:-25px!important;
  margin-left:-25px!important;
  padding: 0;
  cursor: pointer;
  opacity: 1;
  border: 0.06666em solid #fff;
  background-color: #2B333F;
  background-color: rgba(43, 51, 63, 0.7);
  border-radius: 50%!important;
  -webkit-transition: all 0.4s;
  transition: all 0.4s; 
}

</style>
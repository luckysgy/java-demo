// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: UploadFile.proto

package com.demo.sdk;

public interface UploadRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:demo.UploadRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .demo.UploadDataRequest data = 1;</code>
   */
  java.util.List<com.demo.sdk.UploadDataRequest> 
      getDataList();
  /**
   * <code>repeated .demo.UploadDataRequest data = 1;</code>
   */
  com.demo.sdk.UploadDataRequest getData(int index);
  /**
   * <code>repeated .demo.UploadDataRequest data = 1;</code>
   */
  int getDataCount();
  /**
   * <code>repeated .demo.UploadDataRequest data = 1;</code>
   */
  java.util.List<? extends com.demo.sdk.UploadDataRequestOrBuilder> 
      getDataOrBuilderList();
  /**
   * <code>repeated .demo.UploadDataRequest data = 1;</code>
   */
  com.demo.sdk.UploadDataRequestOrBuilder getDataOrBuilder(
      int index);
}
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DemoService.proto

package com.demo.sdk;

public interface DemoRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:demo.DemoRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <code>string serialNum = 2;</code>
   * @return The serialNum.
   */
  java.lang.String getSerialNum();
  /**
   * <code>string serialNum = 2;</code>
   * @return The bytes for serialNum.
   */
  com.google.protobuf.ByteString
      getSerialNumBytes();

  /**
   * <code>string userNum = 3;</code>
   * @return The userNum.
   */
  java.lang.String getUserNum();
  /**
   * <code>string userNum = 3;</code>
   * @return The bytes for userNum.
   */
  com.google.protobuf.ByteString
      getUserNumBytes();

  /**
   * <code>int32 status = 4;</code>
   * @return The status.
   */
  int getStatus();

  /**
   * <code>int32 type = 5;</code>
   * @return The type.
   */
  int getType();

  /**
   * <code>string message = 6;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 6;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>string createTime = 7;</code>
   * @return The createTime.
   */
  java.lang.String getCreateTime();
  /**
   * <code>string createTime = 7;</code>
   * @return The bytes for createTime.
   */
  com.google.protobuf.ByteString
      getCreateTimeBytes();

  /**
   * <code>string updateTime = 8;</code>
   * @return The updateTime.
   */
  java.lang.String getUpdateTime();
  /**
   * <code>string updateTime = 8;</code>
   * @return The bytes for updateTime.
   */
  com.google.protobuf.ByteString
      getUpdateTimeBytes();
}
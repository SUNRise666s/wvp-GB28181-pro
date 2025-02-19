package com.genersoft.iot.vmp.web.gb28181;

import com.alibaba.fastjson.JSONObject;
import com.genersoft.iot.vmp.conf.exception.ControllerException;
import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.gb28181.transmit.cmd.impl.SIPCommander;
import com.genersoft.iot.vmp.storager.IVideoManagerStorage;
import com.genersoft.iot.vmp.vmanager.bean.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;

/**
 * API兼容：设备控制
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/control")
public class ApiControlController {

    private final static Logger logger = LoggerFactory.getLogger(ApiControlController.class);

    @Autowired
    private SIPCommander cmder;

    @Autowired
    private IVideoManagerStorage storager;

    /**
     * 设备控制 - 云台控制
     * @param serial 设备编号
     * @param command 控制指令 允许值: left, right, up, down, upleft, upright, downleft, downright, zoomin, zoomout, stop
     * @param channel 通道序号
     * @param code 通道编号
     * @param speed 速度(0~255) 默认值: 129
     * @return
     */
    @RequestMapping(value = "/ptz")
    private void list(String serial,String command,
                            @RequestParam(required = false)Integer channel,
                            @RequestParam(required = false)String code,
                            @RequestParam(required = false)Integer speed){

        if (logger.isDebugEnabled()) {
            logger.debug("模拟接口> 设备云台控制 API调用，deviceId：{} ，channelId：{} ，command：{} ，speed：{} ",
                    serial, code, command, speed);
        }
        if (channel == null) {channel = 0;}
        if (speed == null) {speed = 0;}
        Device device = storager.queryVideoDevice(serial);
        if (device == null) {
            throw new ControllerException(ErrorCode.ERROR100.getCode(), "device[ " + serial + " ]未找到");
        }
        int cmdCode = 0;
        switch (command){
            case "left":
                cmdCode = 2;
                break;
            case "right":
                cmdCode = 1;
                break;
            case "up":
                cmdCode = 8;
                break;
            case "down":
                cmdCode = 4;
                break;
            case "upleft":
                cmdCode = 10;
                break;
            case "upright":
                cmdCode = 9;
                break;
            case "downleft":
                cmdCode = 6;
                break;
            case "downright":
                cmdCode = 5;
                break;
            case "zoomin":
                cmdCode = 16;
                break;
            case "zoomout":
                cmdCode = 32;
                break;
            case "stop":
                cmdCode = 0;
                break;
            default:
                break;
        }
        // 默认值 50
        try {
            cmder.frontEndCmd(device, code, cmdCode, speed, speed, speed);
        } catch (SipException | InvalidArgumentException | ParseException e) {
            logger.error("[命令发送失败] 云台控制: {}", e.getMessage());
            throw new ControllerException(ErrorCode.ERROR100.getCode(), "命令发送失败: " + e.getMessage());
        }
    }
}

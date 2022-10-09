package cn.lijunhua.patient.controller;


import cn.lijunhua.patient.config.WebaseAppConfig;
import cn.lijunhua.patient.entity.PatientInfo;
import cn.lijunhua.patient.utils.WebaseAppApi;
import com.webank.webase.app.sdk.client.AppClient;
import com.webank.webase.app.sdk.dto.req.ReqAppRegister;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trans")
@Api(tags = "患者招募服务")
public class PatientController {

    @Autowired
    private WebaseAppConfig webaseAppConfig;

    @ResponseBody
    @ApiOperation(value = "患者信息注册",notes = "患者信息注册")
    @PostMapping(value = "/create")
    public Object create(@RequestBody PatientInfo request) throws Exception {
        return WebaseAppApi.newUser(webaseAppConfig,request.getGroup(),request.getPhone(),request.getEmail());
    }
}

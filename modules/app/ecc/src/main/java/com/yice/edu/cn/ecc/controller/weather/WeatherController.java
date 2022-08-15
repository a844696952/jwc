package com.yice.edu.cn.ecc.controller.weather;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.ecc.service.school.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Api(value = "/weather",description = "天气预报，接口文档:https://lbs.amap.com/api/webservice/guide/api/weatherinfo")
public class WeatherController {
    @Autowired
    private SchoolService schoolService;
    @GetMapping("/getWeatherInfo/{schoolId}")
    @ApiOperation(value = "通过学校编号，获取到当前的天气信息", notes = "返回响应对象")
    public ResponseJson lookSchoolById(
            @ApiParam(value = "获取学校天气,需要用到的id", required = true)
            @PathVariable("schoolId") String schoolId){
        try{
            School school=schoolService.findSchoolById(schoolId);
            //拼凑成省市县及名称
            String address = school.getPropertyName()+school.getCityName()+school.getDistrictName()+school.getName();
            //获取到当前学校的code值
            String result= HttpUtil.get("https://restapi.amap.com/v3/geocode/geo?address="+address+"&output=json&key=4dc3c90281393be57cbf9ba6b41722f5");
            JSONObject json = new JSONObject(result);
            String adcode = json.getJSONArray("geocodes").getJSONObject(0).get("adcode").toString();
            //获取到天气详情
            String weatherInfo= HttpUtil.get("https://restapi.amap.com/v3/weather/weatherInfo?city="+adcode+"&key=4dc3c90281393be57cbf9ba6b41722f5&extensions=all");
            return new ResponseJson( new JSONObject(weatherInfo));
        }catch (Exception e){
            return new ResponseJson(false,"学校不存在");
        }
    }
}

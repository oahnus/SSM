package cn.edu.just.controller;

import cn.edu.just.pojo.Company;
import cn.edu.just.pojo.Data;
import cn.edu.just.service.ICompanyService;
import cn.edu.just.util.ApplicationContextConfig;
import cn.edu.just.util.ExcelReader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 操作公司信息的增添,查询,删除
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/company")
public class CompanyController {

    private Logger logger = LoggerFactory.getLogger(CompanyController.class);
    ApplicationContext appContext = ApplicationContextConfig.getApplicationContext();
    ICompanyService companyService = (ICompanyService) appContext.getBean("companyService");

    /**
     * 获取所有公司的信息
     * @return 结果信息,包含status,info,公司信息
     */
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=get"})
    @ResponseBody
    public Map getCompanyList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();

        List<Company> companyList = companyService.getCompanyList();

        map.put("status","success");
        map.put("info","获取公司信息成功");
        map.put("data",companyList);

        return map;
    }

    /**
     * 根据用户名获取公司信息
     * @param username 用户名(公司名)
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/{username}/",method = RequestMethod.POST,headers = {"method=get"})
    public Map getCompanyInfo(@PathVariable String username){
        Map<String,Object> map = new HashMap<>();

        Company company = companyService.getCompanyInfo(username);
        if(company != null) {
            map.put("status", "success");
            map.put("info","获取公司信息成功");
        }else{
            map.put("status", "error");
            map.put("info","获取信息失败");
        }
        map.put("data", company);
        return map;
    }

    /**
     * 从Excel批量添加公司信息
     * @param excelFile 包含公司信息的Excel文件
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=post"})
    public Map insertCompany(@RequestBody MultipartFile excelFile, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/temp/");

        // 路径不存在则创建
        File testFile = new File(path);
        if(!testFile.exists()) testFile.mkdirs();

        Map<String,Object> map = new HashMap<>();

        if(!excelFile.isEmpty()) {
            // 获取文件后缀
            String filename = excelFile.getOriginalFilename();

            // 将Excel文件保存到本地后，进行读取
            FileUtils.copyInputStreamToFile(excelFile.getInputStream(), new File(path,filename));

            File file = new File(path,filename);

            // 导入公司数据，3代表公司角色
            List<Company> list = ExcelReader.readExcelFile(file,3);

            if(list != null) {
                List<Company> insertCompanyList = companyService.insertCompany(list);
                map.put("status","success");
                map.put("info","导入公司信息成功");
                map.put("data",insertCompanyList);
            }else{
                map.put("status","error");
                map.put("info","Excel解析失败,请检查Excel格式");
            }
            file.delete();
        }else{
            map.put("status","error");
            map.put("info","导入数据失败");
        }
        return map;
    }

    /**
     * 根据id批量删除公司信息
     * @param data Bean 用于接收JSON数据中的id对象
     * @return 返回删除操作成功或失败的状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.POST,headers = {"method=delete"})
    public Map deleteCompany(@RequestBody Data<CompanyID> data){

        List<CompanyID> companyIdList = data.getData();
        int[] ids = new int[companyIdList.size()];

        Map<String,Object> map = new HashMap<>();

        companyService.deleteCompanyBatch(ids);

        map.put("status","success");
        map.put("info","删除公司信息成功");

        return map;
    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseBody
//    public Map handleError(){
//        Map map = new HashMap<>();
//        map.put("status","error");
//        map.put("info","HttpRequestMethodNotSupportedException");
//        return map;
//    }

    // 接收json中的公司id,实现批量删除
    static class CompanyID {
        private int id;

        public CompanyID(){super();}

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
    }
}

package com.tt.teach.controller;

import com.tt.teach.pojo.Student;
import com.tt.teach.service.StudentService;
import com.tt.teach.utils.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Request;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/stu")
public class StudentController extends BaseController{

    @Resource
    private StudentService studentService;

    @RequestMapping("/login")
    public String login(){
        return "/student/login";
    }

    @RequestMapping("/student")
    public String student(){
        return "/student/student";
    }


    @RequestMapping("/index")
    public String index() {
        String studentName = (String)getSession().getAttribute(SESSION_KEY);
        if (studentName!=null){
            return "/student/index";
        }
        return REDIRECT+"/stu/login";
    }

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpSession session) {
        String xuehao = request.getParameter("studentNo");
        Integer studentNo = Integer.parseInt(xuehao);
        String loginPwd = request.getParameter("loginPwd");
        Student student = new Student();
        student.setStudentNo(studentNo);
        student.setLoginPwd(loginPwd);
        Student student1 = studentService.doLogin(student);
        if (student1!=null){
            session.setAttribute(SESSION_KEY,student1.getStudentName());
            return FORWARD+"/stu/index";
        }
        return REDIRECT+"/stu/login";
    }

    @RequestMapping("/logout")
    public String logout(){
        getSession().removeAttribute(SESSION_KEY);
        return REDIRECT+"/stu/login";
    }

    @RequestMapping(value = "/getStudentList",method = RequestMethod.GET)
    @ResponseBody
    public Object getStudentList() {
        List<Student> list = studentService.getStudentList();
        return list;
    }
    @RequestMapping(value = "/deleteStudent",method = RequestMethod.POST)
    public String deleteStudent(){
        return "明儿  见";
    }

}

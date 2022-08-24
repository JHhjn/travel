package com.hniu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hniu.travel.bean.Result;
import com.hniu.travel.mapper.MemberMapper;
import com.hniu.travel.pojo.Member;
import com.hniu.travel.util.MailUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberMapper mapper;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private MailUtils mailUtils;
    @Value("${project.path}")
    private String projectPath;


    public Result register(Member member){

        //1注册
            //验证用户名是否重复
            List<Member> usernames = mapper.selectList(new QueryWrapper<Member>().eq("username", member.getUsername()));
           if (usernames.size()>0)
               return new Result(false,"亲，用户名已存在，请重新输入正确的用户名");

            //验证手机号是否重复
            List<Member> phoneNums = mapper.selectList(new QueryWrapper<Member>().eq("phoneNum", member.getPhoneNum()));
            if (phoneNums.size()>0)
                return new Result(false,"亲，手机号已被绑定，请重新输入正确的手机号");

        //验证邮箱是否重复
            List<Member> emails = mapper.selectList(new QueryWrapper<Member>().eq("email", member.getEmail()));
           if (emails.size()>0)
               return new Result(false,"亲，邮箱已被绑定，请重新输入正确的邮箱");


            //给密码加密
            String password = encoder.encode(member.getPassword());
            member.setPassword(password);
            //设置状态码
            member.setActive(false);
         // 2.发送激活邮件
        // 生成激活码
        String activeCode = UUID.randomUUID().toString();
        // 给用户的邮箱发送一封邮件，该邮件包含一个链接，链接中包含激活码
        String activeUrl = projectPath+"/frontdesk/member/active?activeCode="+activeCode;
        String text = "恭喜您注册成功！<a href = '"+activeUrl+"'>点击激活</a>完成账号认证";
        mailUtils.sendMail(member.getEmail(),text,"旅游网激活邮件");

        // 保存激活码，激活时比对
        member.setActiveCode(activeCode);
        // 保存用户
        mapper.insert(member);
        return new Result(true,"注册成功！");
    }
    public String active(String activeCode){
        //根据激活码查询用户
        Member member = mapper.selectOne(new QueryWrapper<Member>().eq("activeCode", activeCode));
        if (member==null){
            return "亲,激活失败!,激活码错误哦!";
        }else {
            member.setActive(true);
            mapper.updateById(member);
            return "亲,激活成功,可以点击<a href='"+projectPath+"/frontdesk/login'>登陆</a>啦!";
        }
    }

    public Result login(String name,String password){
        Member member=null;
        //判断用户名是否存在
        if (member==null){
            member=mapper.selectOne(new QueryWrapper<Member>().eq("username", name));
        }
        //判断用户手机号是否存在
      if (member==null){
          member=mapper.selectOne(new QueryWrapper<Member>().eq("phoneNum", name));
      }
        //判断用户邮箱是否存在
        if (member==null){

            member=mapper.selectOne(new QueryWrapper<Member>().eq("email", name));
        }
        //用户不存在
        if (member==null){
            return new Result(false,"用户名或密码有误，请重新输入");
        }
        //用户是否激活
        if (!member.isActive()){
            return new Result(false, "用户未激活，请登陆邮箱激活");
        }
        //判断密码是否正确
        if (!encoder.matches(password,member.getPassword())) {
            return new Result(false,"用户名或密码有误，请重新输入");
        }

        Result result = new Result(true, "登陆成功",member);

        return result;
    }
}

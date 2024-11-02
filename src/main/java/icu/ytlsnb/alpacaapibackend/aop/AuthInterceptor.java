package icu.ytlsnb.alpacaapibackend.aop;

import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.model.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.UNAUTHORIZED;

@Aspect
@Component
public class AuthInterceptor {
    private final HttpSession session;

    public AuthInterceptor(HttpSession session) {
        this.session = session;
    }

    // 切点：匹配所有标注了 @AdminRequired 的方法
    @Pointcut("@annotation(icu.ytlsnb.alpacaapibackend.annotation.AdminRequire)")
    public void adminRequiredMethods() {
    }

    // 前置通知：在方法执行前进行权限校验
    @Before("adminRequiredMethods()")
    public void checkAdminAccess() {
        User user = (User) session.getAttribute("user");
        Integer role = user.getRole();
        if (role == null || role != 1) {
            throw new BusinessException(UNAUTHORIZED, "需要管理员权限...");
        }
    }
}

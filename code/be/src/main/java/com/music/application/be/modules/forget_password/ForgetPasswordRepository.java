package com.music.application.be.modules.forget_password;

import com.music.application.be.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, Integer> {

    @Query("select fp from ForgetPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgetPassword> findByUserAndOtp(Integer otp, User user);

    Optional<ForgetPassword> findByUser(User user);
}

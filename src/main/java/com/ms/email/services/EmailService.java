package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.Email;
import com.ms.email.repositories.EmailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public Email sendEmail(Email emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SEND);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }
        return emailRepository.save(emailModel);

    }

    public void reSendEmail(StatusEmail statusEmail) {
        statusEmail = statusEmail == null ? null : statusEmail;
        statusEmail.ordinal();
        List<Email> emails = emailRepository.findAllByStauts(statusEmail.ordinal());
        for (Email email : emails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(email.getEmailFrom());
                message.setTo(email.getEmailTo());
                message.setSubject(email.getSubject());
                message.setText(email.getText());
                emailSender.send(message);
                email.setStatusEmail(StatusEmail.SEND);
            } catch (MailException e) {
                email.setStatusEmail(StatusEmail.ERROR);
            }
            emailRepository.save(email);
        }
    }

    public Page<Email> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    public Optional<Email> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}

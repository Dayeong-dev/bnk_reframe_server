package com.example.reframe.service.fcm;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.reframe.dto.fcm.FcmSendDto;

@Service
public interface FcmServiceIn {

	int sendMessageTo(FcmSendDto fcmSendDto) throws IOException;
}

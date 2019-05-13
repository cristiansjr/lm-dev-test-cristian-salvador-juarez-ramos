package com.lifebank.process;

import com.lifebank.pojo.RequestEncryptionDecryptionPojo;
import com.lifebank.pojo.ResponseEncryptionDecryptionPojo;

public interface EncryptionDecryptionProcess {
	public ResponseEncryptionDecryptionPojo process(RequestEncryptionDecryptionPojo request);
}

package ar.com.avaco.ws.service.impl;

import java.util.List;
import java.util.Map;

import ar.com.avaco.ws.dto.attachment.ResponseAttachmentGetPost;

public interface AttachmentSapService {

	ResponseAttachmentGetPost getAttachment(Long attachmentEntry);

	Long enviarAttachmentsSap(List<Map<String, String>> attachments);

}

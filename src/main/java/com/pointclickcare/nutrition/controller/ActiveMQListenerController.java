/*
 * package com.pointclickcare.nutrition.controller;
 * import java.util.ArrayList;
 * import java.util.HashMap;
 * import java.util.List;
 * import java.util.Map;
 * import javax.jms.JMSException;
 * import javax.jms.Message;
 * import javax.jms.TextMessage;
 * import javax.servlet.http.HttpServletResponse;
 * import org.springframework.http.MediaType;
 * import org.springframework.jms.annotation.JmsListener;
 * import org.springframework.stereotype.Controller;
 * import org.springframework.web.bind.annotation.PathVariable;
 * import org.springframework.web.bind.annotation.RequestMapping;
 * import org.springframework.web.bind.annotation.RequestMethod;
 * import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * import com.momentum.dms.domain.dto.MealOrderDTO;
 * @Controller
 * public class ActiveMQListenerController
 * {
 * private Map<Long, List<SseEmitter>> sseEmitterFacilityMap = new HashMap<Long,
 * List<SseEmitter>>();
 * private static final String TOPIC = "dev";
 * @JmsListener(destination = TOPIC)
 * public void receiveMessage(final Message message) throws JMSException
 * {
 * TextMessage textMessage = (TextMessage) message;
 * String payload = textMessage.getText();
 * ObjectMapper mapper = new ObjectMapper();
 * try
 * {
 * MealOrderDTO mealOrderDTO = mapper.readValue(payload, MealOrderDTO.class);
 * List<SseEmitter> facilityEmitters = sseEmitterFacilityMap.get(mealOrderDTO.getFacilityId());
 * if (facilityEmitters != null && !facilityEmitters.isEmpty())
 * {
 * List<SseEmitter> emittersToRmv = new ArrayList<SseEmitter>();
 * facilityEmitters.parallelStream().forEach((emitter) -> {
 * try
 * {
 * emitter.send(mealOrderDTO.getId());
 * }
 * catch (Exception e)
 * {
 * emitter.completeWithError(e);
 * emittersToRmv.add(emitter);
 * }
 * });
 * facilityEmitters.removeAll(emittersToRmv);
 * sseEmitterFacilityMap.put(mealOrderDTO.getFacilityId(), facilityEmitters);
 * }
 * }catch(Exception ex)
 * {
 * ex.printStackTrace();
 * }
 * }
 * @RequestMapping(value = "/getSseChannel/{facilityId}", method = RequestMethod.GET, produces =
 * MediaType.TEXT_EVENT_STREAM_VALUE)
 * public SseEmitter createSseChannel(@PathVariable Long facilityId, HttpServletResponse response)
 * {
 * SseEmitter emitter = new SseEmitter(0l);
 * if (sseEmitterFacilityMap.get(facilityId) == null)
 * {
 * sseEmitterFacilityMap.put(facilityId, new ArrayList<SseEmitter>());
 * }
 * sseEmitterFacilityMap.get(facilityId).add(emitter);
 * emitter.onCompletion(() -> {
 * sseEmitterFacilityMap.get(facilityId).remove(emitter);
 * });
 * emitter.onTimeout(() -> {
 * sseEmitterFacilityMap.get(facilityId).remove(emitter);
 * });
 * return emitter;
 * }
 * }
 */
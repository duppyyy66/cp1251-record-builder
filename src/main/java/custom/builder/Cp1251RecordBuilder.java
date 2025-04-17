package custom.builder;

import com.ibm.eventstreams.connect.mqsource.builders.RecordBuilder;
import org.apache.kafka.connect.source.SourceRecord;

import javax.jms.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

public class Cp1251RecordBuilder implements RecordBuilder {

    @Override
    public SourceRecord toSourceRecord(
            JMSContext jmsContext,
            String topic,
            boolean useJmsHeadersAsKafkaHeaders,
            Message mqMessage
    ) {
        // Просто перенаправим вызов в основной метод
        return toSourceRecord(jmsContext, topic, useJmsHeadersAsKafkaHeaders, mqMessage, Collections.emptyMap(), Collections.emptyMap());
    }

    @Override
    public SourceRecord toSourceRecord(
            JMSContext jmsContext,
            String topic,
            boolean useJmsHeadersAsKafkaHeaders,
            Message mqMessage,
            Map<String, Long> messageCounters,
            Map<String, String> customProperties
    ) {
        Object value;

        try {
            if (mqMessage instanceof BytesMessage) {
                BytesMessage bmsg = (BytesMessage) mqMessage;
                byte[] data = new byte[(int) bmsg.getBodyLength()];
                bmsg.readBytes(data);
                value = new String(data, Charset.forName("Windows-1251"));
            } else if (mqMessage instanceof TextMessage) {
                value = ((TextMessage) mqMessage).getText();
            } else {
                value = null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process MQ message", e);
        }

        return new SourceRecord(
                Collections.emptyMap(),
                Collections.emptyMap(),
                topic,
                null,
                null,
                value
        );
    }
}

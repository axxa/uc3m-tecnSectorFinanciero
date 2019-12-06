package com.cnebrera.uc3.tech.lesson9;

import com.cnebrera.uc3.tech.lesson9.jaxb.JaxbSerializer;
import com.cnebrera.uc3.tech.lesson9.json.JsonSerializer;
import com.cnebrera.uc3.tech.lesson9.kryo.KryoSerializer;
import com.cnebrera.uc3.tech.lesson9.model.ReferenceData;
import com.cnebrera.uc3.tech.lesson9.proto.Lesson9;
import com.cnebrera.uc3.tech.lesson9.proto.ProtoSerializer;
import com.google.protobuf.ByteString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main Class that measure the performance
 */
public class Measurement
{
    private static long NUM_ITERATIONS = 100000000;
    /** a org.slf4j.Logger with the instance of this class given by org.slf4j.LoggerFactory */
    private final static Logger LOGGER = LoggerFactory.getLogger(Measurement.class);

    private final static JaxbSerializer jaxbSerializer = new JaxbSerializer();
    private final static JsonSerializer jsonSerializer = new JsonSerializer();
    private final static KryoSerializer kryoSerializer = new KryoSerializer();
    private final static ProtoSerializer protoSerializer = new ProtoSerializer();

    public static void main(String[] args) throws URISyntaxException, IOException
    {


        //Read the info from a xml and populate the class

        URL url = Measurement.class.getClassLoader().getResource("Example.xml");
        URL urlJson = Measurement.class.getClassLoader().getResource("Example.json");

        String str = new String(Files.readAllBytes(Paths.get(url.toURI())));
        String json = new String(Files.readAllBytes(Paths.get(urlJson.toURI())));

        ReferenceData referenceData = jaxbSerializer.deserialize(str);

        LOGGER.debug("[Practica 1] Size of referenceData instrument list {}", referenceData.getListOfInstruments().size());
        LOGGER.debug("[Practica 1] Algorithm identifier{}", referenceData.getAlgorithmIdentifier());
        LOGGER.debug("[Practica 1] Algorithm marketId{}", referenceData.getMarketId());

        LOGGER.debug("[Practica 2] Json Serializer [{}] ", referenceData.equals(jsonSerializer.deserialize(json)));

        Lesson9.ReferenceData.Builder referenceDataBuilder = Lesson9.ReferenceData.newBuilder()
        // TODO set the parameters in the builder using the values read in referenceData from JSON to ensure both have the same contents
        .setAlgorithmIdentifierBytes(ByteString.copyFromUtf8(referenceData.getAlgorithmIdentifier()))
        .setMarketId(referenceData.getMarketId())
        ;

        referenceData.getListOfInstruments().forEach(i -> referenceDataBuilder.addInstrument(Lesson9.Instrument.newBuilder()
                .setInstrumentId(i.getInstrumentId())
                .setSymbol(i.getSymbol()))
        );

        //Test Proto
        Lesson9.ReferenceData referenceDataProto = referenceDataBuilder.build();
        LOGGER.debug("[Practica 3] Proto Serializer [{}] ", referenceDataProto.equals(protoSerializer.deserialize(protoSerializer.serialize(referenceDataProto))));

        //Test Kryo
        LOGGER.debug("[Practica 4] Kryo Serializer [{}] ",referenceData.equals(kryoSerializer.deserialize(kryoSerializer.serialize(referenceData))));

        //Test performance serialization
        testPerformanceSerialization(referenceData, referenceDataProto);

        //Test performance deserialization
        testPerformanceDeSerialization(str, jsonSerializer.serialize(referenceData), kryoSerializer.serialize(referenceData), referenceDataProto.toByteArray());

        //Test performance serialization and deserialization
        testPerformanceSerializationAndDeserialization(referenceData, referenceDataProto);

        //Test performance serialization byte size
        testPerformanceSizeSerialization(referenceData, referenceDataProto);
    }

    private static void testPerformanceSerialization(ReferenceData referenceData, Lesson9.ReferenceData referenceDataProto)
    {
        //JAXB serialization
        long jaxbSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization
            jaxbSerializer.serialize(referenceData);
        }
        long jaxbSerializationFin = System.nanoTime();
        long meanJaxb = (jaxbSerializationFin - jaxbSerializationIni)/NUM_ITERATIONS;

        //Json serialization
        long jsonSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization
            jsonSerializer.serialize(referenceData);
        }
        long jsonSerializationFin = System.nanoTime();
        long meanJson = (jsonSerializationFin - jsonSerializationIni)/NUM_ITERATIONS;

        //Protocol Buffers serialization
        long protoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization
            protoSerializer.serialize(referenceDataProto);
        }
        long protoSerializationFin = System.nanoTime();
        long meanProto = (protoSerializationFin - protoSerializationIni)/NUM_ITERATIONS;

        //Kryo serialization
        long kryoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization
            kryoSerializer.serialize(referenceData);
        }
        long kryoSerializationFin = System.nanoTime();
        long meanKryo = (kryoSerializationFin - kryoSerializationIni)/NUM_ITERATIONS;

        LOGGER.debug("[PerformanceSerialization] meanJaxb: {}", meanJaxb);
        LOGGER.debug("[PerformanceSerialization] meanJson: {}", meanJson);
        LOGGER.debug("[PerformanceSerialization] meanProto: {}", meanProto);
        LOGGER.debug("[PerformanceSerialization] meanKryo: {}", meanKryo);
    }

    private static void testPerformanceDeSerialization(String jaxbSerialize, String jsonSerlize, byte[] kryoSerialize, byte[] protoSerialize)
    {
        //JAXB serialization
        long jaxbSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWithDeserialization
            jaxbSerializer.deserialize(jaxbSerialize);
        }
        long jaxbSerializationFin = System.nanoTime();
        long meanJaxb = (jaxbSerializationFin - jaxbSerializationIni)/NUM_ITERATIONS;

        //Json serialization
        long jsonSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWithDeserialization
            jsonSerializer.deserialize(jsonSerlize);
        }
        long jsonSerializationFin = System.nanoTime();
        long meanJson = (jsonSerializationFin - jsonSerializationIni)/NUM_ITERATIONS;

        //Protocol Buffers serialization
        long protoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWithDeserialization
            protoSerializer.deserialize(protoSerialize);
        }
        long protoSerializationFin = System.nanoTime();
        long meanProto = (protoSerializationFin - protoSerializationIni)/NUM_ITERATIONS;

        //Kryo serialization
        long kryoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWithDeserialization
            kryoSerializer.deserialize(kryoSerialize);
        }
        long kryoSerializationFin = System.nanoTime();
        long meanKryo = (kryoSerializationFin - kryoSerializationIni)/NUM_ITERATIONS;

        LOGGER.debug("[PerformanceDeSerialization] meanJaxb: {}", meanJaxb);
        LOGGER.debug("[PerformanceDeSerialization] meanJson: {}", meanJson);
        LOGGER.debug("[PerformanceDeSerialization] meanProto: {}", meanProto);
        LOGGER.debug("[PerformanceDeSerialization] meanKryo: {}", meanKryo);
    }

    private static void testPerformanceSerializationAndDeserialization(ReferenceData referenceData, Lesson9.ReferenceData referenceDataProto)
    {
        //JAXB serialization
        long jaxbSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization And Deserialization
            jaxbSerializer.deserialize(jaxbSerializer.serialize(referenceData));
        }
        long jaxbSerializationFin = System.nanoTime();
        long meanJaxb = (jaxbSerializationFin - jaxbSerializationIni)/NUM_ITERATIONS;

        //Json serialization
        long jsonSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization And Deserialization
            jsonSerializer.deserialize(jsonSerializer.serialize(referenceData));
        }
        long jsonSerializationFin = System.nanoTime();
        long meanJson = (jsonSerializationFin - jsonSerializationIni)/NUM_ITERATIONS;

        //Protocol Buffers serialization
        long protoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization And Deserialization
            protoSerializer.deserialize(protoSerializer.serialize(referenceDataProto));
        }
        long protoSerializationFin = System.nanoTime();
        long meanProto = (protoSerializationFin - protoSerializationIni)/NUM_ITERATIONS;

        //Kryo serialization
        long kryoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++)
        {
            //TODO fillWith Serialization And Deserialization
            kryoSerializer.deserialize(kryoSerializer.serialize(referenceData));
        }
        long kryoSerializationFin = System.nanoTime();
        long meanKryo = (kryoSerializationFin - kryoSerializationIni)/NUM_ITERATIONS;

        LOGGER.debug("[PerformanceSerializationAndDeserialization] meanJaxb: {}", meanJaxb);
        LOGGER.debug("[PerformanceSerializationAndDeserialization] meanJson: {}", meanJson);
        LOGGER.debug("[PerformanceSerializationAndDeserialization] meanProto: {}", meanProto);
        LOGGER.debug("[PerformanceSerializationAndDeserialization] meanKryo: {}", meanKryo);
    }

    private static void testPerformanceSizeSerialization(ReferenceData referenceData, Lesson9.ReferenceData referenceDataProto)
    {
        LOGGER.debug("[PerformanceSizeSerialization] Jaxb: {} bytes", jaxbSerializer.serialize(referenceData).getBytes().length );
        LOGGER.debug("[PerformanceSizeSerialization] Json: {} bytes", jsonSerializer.serialize(referenceData).getBytes().length );
        LOGGER.debug("[PerformanceSizeSerialization] Proto: {} bytes", protoSerializer.serialize(referenceDataProto).length );
        LOGGER.debug("[PerformanceSizeSerialization] Kryo: {} bytes", kryoSerializer.serialize(referenceData).length );
        
    }
}


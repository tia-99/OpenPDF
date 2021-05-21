package com.lowagie.text.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.Security;
import java.util.List;

public class CheckByteRangeGapTest {

    @Test
    void testSWA() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        InputStream moddedFile = new FileInputStream("/siwa.pdf");
        PdfReader reader = new PdfReader(moddedFile);
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        AcroFields fields = new AcroFields(reader, writer);
        List<String> names = fields.getSignedFieldNames();
        for (String signName : names) {
            Assertions.assertFalse(fields.checkByteRangeGap(signName));
        }
    }

    @Test
    void testCorrectSignature() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        InputStream moddedFile = new FileInputStream("/sample_signed-sha512.pdf");
        PdfReader reader = new PdfReader(moddedFile);
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        AcroFields fields = new AcroFields(reader, writer);
        List<String> names = fields.getSignedFieldNames();
        String name = names.get(0);
        Assertions.assertTrue(fields.checkByteRangeGap(name));
    }
}

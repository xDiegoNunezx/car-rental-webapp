package edu.unam.dgtic.proyecto_final.system.util;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;

@Component
public class PdfGenerator {

    public ByteArrayOutputStream generarPdfReserva(Reserva reserva) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Encabezado
        Paragraph header = new Paragraph("COMPROBANTE DE RESERVA")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLUE);
        document.add(header);

        // Logo (opcional)
        try {
            // Usar ClassPathResource para cargar la imagen desde el classpath
            ClassPathResource resource = new ClassPathResource("static/image/autos.png");
            ImageData imageData = ImageDataFactory.create(resource.getInputStream().readAllBytes());
            Image logo = new Image(imageData).setWidth(100).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
        } catch (IOException e) {
            System.out.println("Logo no encontrado, continuando sin él: " + e.getMessage());
        }

        // Información de la reserva
        document.add(new Paragraph("\n"));

        // Tabla con los datos
        float[] columnWidths = {2, 5};
        Table table = new Table(columnWidths);

        // Datos del cliente
        addRow(table, "Número de Reserva:", reserva.getId().toString());
        addRow(table, "Fecha de Reserva:", formatDate(reserva.getFechaReserva()));
        addRow(table, "Cliente:", reserva.getCliente().getNombreCompleto());
        addRow(table, "RFC:", reserva.getCliente().getRfc());
        addRow(table, "Licencia:", reserva.getCliente().getNumeroLicencia());

        // Datos del vehículo
        Vehiculo vehiculo = reserva.getVehiculo();
        addRow(table, "Vehículo:", vehiculo.getMarca().getNombre() + " " + vehiculo.getModelo());
        addRow(table, "Placa:", vehiculo.getNumeroPlaca());
        addRow(table, "Tipo:", vehiculo.getTipoCarroceria().getDescripcion());
        addRow(table, "Transmisión:", vehiculo.getTransmision().getDescripcion());
        addRow(table, "Precio por día:", "$" + String.format("%.2f", vehiculo.getPrecioDia()));

        // Fechas de alquiler
        addRow(table, "Fecha de Inicio:", formatDate(reserva.getFechaInicio()));
        addRow(table, "Fecha de Fin:", formatDate(reserva.getFechaFin()));
        addRow(table, "Días de alquiler:", String.valueOf(reserva.getFechaInicio().until(reserva.getFechaFin()).getDays()));

        // Extras
        String extras = "";
        if (reserva.isAsientoInfantil()) extras += "Asiento Infantil, ";
        if (reserva.isAsientoElevador()) extras += "Asiento Elevador, ";
        if (reserva.isConductoresAdicionales()) extras += "Conductores Adicionales";
        if (extras.isEmpty()) extras = "Ninguno";
        extras = extras.replaceAll(", $", "");
        addRow(table, "Extras:", extras);

        // Total
        addRow(table, "Total a Pagar:", "$" + String.format("%.2f", reserva.getPagoTotal()));

        document.add(table);

        // Términos y condiciones
        Paragraph terms = new Paragraph("\nTérminos y condiciones:\n" +
                "1. El vehículo debe ser devuelto en las mismas condiciones.\n" +
                "2. Cargos adicionales pueden aplicarse por daños o retrasos.\n" +
                "3. El combustible no incluido debe ser repuesto.\n")
                .setFontSize(10)
                .setItalic();
        document.add(terms);

        // Pie de página
        Paragraph footer = new Paragraph("Gracias por su reserva - " +
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(java.time.LocalDate.now()))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10);
        document.add(footer);

        document.close();
        return baos;
    }

    private void addRow(Table table, String header, String value) {
        table.addCell(new Cell().add(new Paragraph(header).setBold()));
        table.addCell(new Cell().add(new Paragraph(value != null ? value : "")));
    }

    private String formatDate(java.time.LocalDate date) {
        return date != null ?
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date) : "";
    }
}
package com.skaliy.mobilecom.client.panes;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PaneRecord extends AnchorPane {

    public final static int
            PANE_MAIN = 1,
            PANE_TARIFF = 2,
            PANE_OFFER = 3,
            PANE_PHONE = 4;
    private final int THIS_PANE;

    private static double layoutYNextPane = 10;

    private String title, general, availableOffers,
            manufacturer, model, os, processor, resolution, color;
    private double prefWidth, price, diagonal, cameraMain, cameraMainTwo, cameraFront;
    private boolean memoryCard = false;
    private Date dateStart, dateEnd;
    private int record, ram, rom, simcardQuant, batary, units, indexLabelOrder;

    public PaneRecord(int PANE, String[] values) {
        THIS_PANE = PANE;
        record = Integer.parseInt(values[0]);

        if (THIS_PANE == PANE_MAIN) {
            title = values[1];
            general = values[2];

            prefWidth = 625;

        } else if ((THIS_PANE == PANE_TARIFF) || (THIS_PANE == PANE_OFFER)) {
            title = values[1];
            try {
                price = Float.parseFloat(values[2]);
            } catch (NumberFormatException e) {
                price = 0.00;
            }

            prefWidth = 625;

            if (THIS_PANE != PANE_OFFER) {
                general = values[3];
                availableOffers = values[4];
            } else {
                try {
                    dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(values[3]);
                } catch (ParseException e) {
                    dateStart = new Date();
                }
                try {
                    dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(values[4]);
                } catch (ParseException e) {
                    dateEnd = new Date();
                }
                general = values[5];
            }
        } else if (THIS_PANE == PANE_PHONE) {
            manufacturer = values[1];
            model = values[2];
            os = values[3];
            ram = Integer.parseInt(values[4]);
            rom = Integer.parseInt(values[5]);
            memoryCard = Boolean.parseBoolean(values[6]);
            simcardQuant = Integer.parseInt(values[7]);
            processor = values[8];
            batary = Integer.parseInt(values[9]);
            diagonal = Float.parseFloat(values[10]);
            resolution = values[11];
            cameraMain = Float.parseFloat(values[12]);
            try {
                cameraMainTwo = Float.parseFloat(values[13]);
            } catch (NumberFormatException e) {
                cameraMainTwo = 0.00;
            }
            cameraFront = Float.parseFloat(values[14]);
            color = values[15];
            try {
                price = Float.parseFloat(values[16]);
            } catch (NumberFormatException e) {
                price = 0.00;
            }

            units = Integer.parseInt(values[17]);

            prefWidth = 410;
        }
        createPane();
    }

    private void createPane() {
        double prefHeight = 0;

        if (THIS_PANE != PANE_PHONE) {

            Label labelTitle = newLabel(title, 25, 2, 10);

            Separator separatorTitle = newSeparator(
                    labelTitle.getLayoutY() + labelTitle.getPrefHeight() + 10);

            Label labelGeneral = newLabel(general,
                    14, 1, separatorTitle.getLayoutY() + 13);

            getChildren().addAll(labelTitle, separatorTitle, labelGeneral);

            if (THIS_PANE == PANE_MAIN) {
                prefHeight = labelGeneral.getLayoutY() + labelGeneral.getPrefHeight() + 10;

            } else if ((THIS_PANE == PANE_TARIFF) || THIS_PANE == PANE_OFFER) {
                Separator separatorGeneral = newSeparator(
                        labelGeneral.getLayoutY() + labelGeneral.getPrefHeight() + 10);

                Label labelPrice = newLabel(
                        "Стоимость: " + price + " грн/м",
                        14, 2, separatorGeneral.getLayoutY() + 13);

                getChildren().addAll(separatorGeneral, labelPrice);

                if (THIS_PANE == PANE_TARIFF) {
                    if (availableOffers.length() > 3) {

                        Separator separatorPrice = newSeparator(
                                labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 10);

                        Label labelAvailable1Offers = newLabel(
                                "Доступные услуги:\n\n" + availableOffers,
                                14, 2, separatorPrice.getLayoutY() + 10);

                        Separator separatorAvailable1Offers = newSeparator(
                                labelAvailable1Offers.getLayoutY() + labelAvailable1Offers.getPrefHeight() + 10);

                        Label labelOrder = newLabel(
                                "Добавить в корзину",
                                14, 2, separatorAvailable1Offers.getLayoutY() + 13);
                        labelOrder.setCursor(Cursor.HAND);

                        getChildren().addAll(separatorPrice, labelAvailable1Offers, separatorAvailable1Offers, labelOrder);
                        indexLabelOrder = getChildren().size() - 1;

                        prefHeight = labelOrder.getLayoutY() + labelOrder.getPrefHeight() + 20;
                    } else {
                        Separator separatorPrice = newSeparator(
                                labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 10);

                        Label labelOrder = newLabel(
                                "Добавить в корзину",
                                14, 2, separatorPrice.getLayoutY() + 13);
                        labelOrder.setCursor(Cursor.HAND);

                        getChildren().addAll(separatorPrice, labelOrder);
                        indexLabelOrder = getChildren().size() - 1;

                        prefHeight = labelOrder.getLayoutY() + labelOrder.getPrefHeight() + 10;
                    }
                } else {
                    Separator separatorPrice = newSeparator(
                            labelPrice.getLayoutY() + labelPrice.getPrefHeight() + 10);

                    Label labelDate = newLabel(
                            "Срок действия: "
                                    + dateStart.toLocaleString().substring(0, 10)
                                    + " - " + dateEnd.toLocaleString().substring(0, 10),
                            14, 2, separatorPrice.getLayoutY() + 13);

                    getChildren().addAll(separatorPrice, labelDate);

                    prefHeight = labelDate.getLayoutY() + labelDate.getPrefHeight() + 10;
                }
            }

        } else {

            Label labelPhone = newLabel(
                    manufacturer + " " + model,
                    20, 2, 10);

            Separator separatorPhone = newSeparator(
                    labelPhone.getLayoutY() + labelPhone.getPrefHeight() + 10);

            Label labelParameters = newLabel(
                    "Цвет: " + color
                            + "\nОС: " + os
                            + "\nRAM: " + ram + " ГБ"
                            + "\nROM: " + rom + " ГБ"
                            + "\nКарта памяти: " + memoryCard
                            + "\nКоличество SIM-карт: " + simcardQuant
                            + "\nПроцессор: " + processor
                            + "\nБатарея: " + batary + " mAh"
                            + "\nДиагональ: " + diagonal + " ''"
                            + "\nРазрешение: " + resolution
                            + "\nОсновная камера: " + cameraMain + " Мп"
                            + (cameraMainTwo != 0.00
                            ? "\nВторая камера: " + cameraMainTwo + " Мп" : "")
                            + "\nФронтальная камера: " + (float) cameraFront + " Мп",
                    14, 1, separatorPhone.getLayoutY() + 13);

            Separator separatorParameters = newSeparator(
                    labelParameters.getLayoutY() + labelParameters.getPrefHeight() + 10);

            Label labelPrice = newLabel(
                    "Стоимость: " + price + " грн",
                    14, 1, separatorParameters.getLayoutY() + 13);
            Label labelAvailability = newLabel(
                    units > 1 ? "Есть в наличии" : "Нет в наличии",
                    14, 2, separatorParameters.getLayoutY() + 13);
            Label labelOrder = newLabel(
                    "Добавить в корзину",
                    14, 3, separatorParameters.getLayoutY() + 13);
            labelOrder.setCursor(Cursor.HAND);
            if (units == 0) labelOrder.setDisable(true);

            getChildren().addAll(
                    labelPhone, separatorPhone,
                    labelParameters, separatorParameters,
                    labelPrice, labelAvailability, labelOrder);

            indexLabelOrder = getChildren().size() - 1;

            prefHeight = labelOrder.getLayoutY() + labelOrder.getPrefHeight() + 10;
        }

        setPrefWidth(prefWidth + 20);
        setPrefHeight(prefHeight);
        setLayoutX(10);
        setLayoutY(layoutYNextPane);

        layoutYNextPane += getPrefHeight() + 10;
    }

    private Label newLabel(String text, int textSize, int alignment, double layoutY) {
        Label label = new Label();

        label.setText(text);
        label.setFont(new Font("Calibri", textSize));
        label.setPrefSize(prefWidth, Math.round(textSize * 1.5) * getCountLines(text));
        label.setLayoutX(10);
        label.setLayoutY(layoutY);

        label.setAlignment(
                alignment == 2 ? Pos.CENTER
                        : alignment == 3 ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        label.setTextAlignment(
                alignment == 2 ? TextAlignment.CENTER
                        : alignment == 3 ? TextAlignment.RIGHT : TextAlignment.LEFT);

        return label;
    }

    private Separator newSeparator(double layoutY) {
        Separator separator = new Separator(Orientation.HORIZONTAL);

        separator.setPrefWidth(prefWidth);
        separator.setLayoutX(10);
        separator.setLayoutY(layoutY);

        return separator;
    }

    private int getCountLines(String search) {
        int countLines = 1;
        for (int i = 0; i < search.length(); i++) {
            if (Objects.equals(String.valueOf(search.charAt(i)), "\n")) {
                countLines++;
            }
        }
        return countLines;
    }

    public static double getAndReplaceHeight() {
        double layout = layoutYNextPane;
        layoutYNextPane = 10;
        return layout;
    }

    public int getTHIS_PANE() {
        return THIS_PANE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public int getSimcardQuant() {
        return simcardQuant;
    }

    public void setSimcardQuant(int simcardQuant) {
        this.simcardQuant = simcardQuant;
    }

    public int getBatary() {
        return batary;
    }

    public void setBatary(int batary) {
        this.batary = batary;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public double getCameraMain() {
        return cameraMain;
    }

    public void setCameraMain(double cameraMain) {
        this.cameraMain = cameraMain;
    }

    public double getCameraFront() {
        return cameraFront;
    }

    public void setCameraFront(double cameraFront) {
        this.cameraFront = cameraFront;
    }

    public double getCameraMainTwo() {
        return cameraMainTwo;
    }

    public void setCameraMainTwo(double cameraMainTwo) {
        this.cameraMainTwo = cameraMainTwo;
    }

    public boolean isMemoryCard() {
        return memoryCard;
    }

    public void setMemoryCard(boolean memoryCard) {
        this.memoryCard = memoryCard;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getPrefPaneWidth() {
        return prefWidth;
    }

    public void setPrefPaneWidth(double prefWidth) {
        this.prefWidth = prefWidth;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public void setIndexLabelOrder(int indexLabelOrder) {
        this.indexLabelOrder = indexLabelOrder;
    }

    public int getIndexLabelOrder() {
        return indexLabelOrder;
    }

}
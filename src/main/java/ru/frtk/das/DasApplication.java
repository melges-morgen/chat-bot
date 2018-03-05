package ru.frtk.das;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.jvnet.hk2.annotations.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class DasApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasApplication.class, args);
    }

    @Service
    static class Receipt {
        private String purpose;

        //дата подачи заявления
        private Date date1 = new Date();

        //дата принятия решения: дать или нет помощь
        private Date date2 = new Date();

        private String name;
        private String phone;
        private String group;
        private String sex;

        private static int FONT_SIZE_SMALL = 16;
        private static int FONT_SIZE_BIG = 32;

        public Receipt(String name, String group, String sex, String phone, String purpose) {
            this.group = group;
            this.sex = sex;
            this.phone = phone;
            this.purpose = purpose;
            this.name = name;
        }

        public Receipt() {}

        public String getPurpose() { return purpose;}
        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public Date getDate1() { return date1; }
        public void setDate1(Date date) { this.date1 = date; }

        public Date getDate2() { return date2; }
        public void setDate2(Date date) { this.date2 = date; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPhone() { return phone; }
        public  String getGroup() { return group; }
        public  String getSex() throws Exception {
            if (sex.equals("male"))
                return "студента";
            else if (sex.equals("female"))
                return "студентки";

            throw new Exception("variable sex is in incorrect format!");
        }

        public void createTemplate() throws Exception {
            Document document = new Document();

            BaseFont times = BaseFont.createFont("bin/TIMES.TTF","cp1251",BaseFont.EMBEDDED);

            PdfWriter.getInstance(document,
                    new FileOutputStream(date1 + ".pdf"));

            document.open();

            // параграф с текстом
            Paragraph student_info = new Paragraph("Директору школы ФРКТ\n" +
                    "\n" +
                    "Дворковичу Александру Викторовичу\n" +
                    "\n" +
                    sex + " группы " + group +"\n" +
                    name + "\n" +
                    "тел: " + phone, new Font(times,14));

            student_info.setSpacingAfter(FONT_SIZE_BIG);
            student_info.setAlignment(Element.ALIGN_RIGHT);
            document.add(student_info);

            Paragraph topic = new Paragraph(
                    "заявление.", new Font(times,14));


            topic.setAlignment(Element.ALIGN_CENTER);
            topic.setSpacingBefore(FONT_SIZE_BIG);
            document.add(topic);


            Paragraph student_reason = new Paragraph(
                    "Прошу оказать мне материальную помощь по причине " + purpose, new Font(times, 14)
            );
            student_reason.setAlignment(Element.ALIGN_CENTER);
            student_reason.setSpacingBefore(FONT_SIZE_BIG);
            document.add(student_reason);


            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String monthName = getMonthName(month);


            Paragraph date_sign1 = new Paragraph("«" + day + "» " + monthName + " "+ year +" г.  " +
                    "_________________ / " +
                    "______________________", new Font(times, 14)
            );

            date_sign1.setAlignment(Element.ALIGN_CENTER);
            date_sign1.setSpacingBefore(FONT_SIZE_BIG);
            document.add(date_sign1);

            Paragraph decision = new Paragraph("Решение стипендиальной комиссии: материальную помощь назначить / н" +
                    "е назначать", new Font(times, 14)
            );

            decision.setAlignment(Element.ALIGN_CENTER);
            decision.setSpacingBefore(FONT_SIZE_BIG);
            document.add(decision);

            cal = Calendar.getInstance();
            cal.setTime(date2);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            monthName = getMonthName(month);

            Paragraph date_sign2 = new Paragraph("«" + day + "» " + monthName + " "+ year +" г.  " +
                    "_________________ / " +
                    "Микоян Ф. А.", new Font(times, 14)
            );

            date_sign2.setAlignment(Element.ALIGN_CENTER);
            date_sign2.setSpacingBefore(FONT_SIZE_BIG);
            document.add(date_sign2);

            document.close();
        }


        private String getMonthName(int month) throws  Exception{
            String monthString;
            switch (month + 1) {
                case 1:  monthString = "Февраль";
                    break;
                case 2:  monthString = "Январь";
                    break;
                case 3:  monthString = "Март";
                    break;
                case 4:  monthString = "Апрель";
                    break;
                case 5:  monthString = "Май";
                    break;
                case 6:  monthString = "Июнь";
                    break;
                case 7:  monthString = "Июль";
                    break;
                case 8:  monthString = "Август";
                    break;
                case 9:  monthString = "Сентябрь";
                    break;
                case 10: monthString = "Октябрь";
                    break;
                case 11: monthString = "Ноябрь";
                    break;
                case 12: monthString = "Декабрь";
                    break;
                default: {

                    throw new Exception("THere is no such month!");
                }
            }

            return monthString;
        }
    }
}


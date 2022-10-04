import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        LocalDateTime localDateTimeLucas = LocalDateTime.of(1993, Month.DECEMBER, 16, 12, 30);
        LocalDateTime localDateTimeDerik = LocalDateTime.of(1991, Month.JUNE, 30, 10, 30);
        LocalDateTime localDateTimeArthur = LocalDateTime.of(1987, Month.MARCH, 25, 19, 30);

        mapaAstral(localDateTimeLucas, "Recife");

    }

    private static void mapaAstral(LocalDateTime dataHoraNascimento, String localNascimento) {
        Period idade = Period.between(dataHoraNascimento.toLocalDate(), LocalDate.now());
        System.out.println("Idade: " + idade.getYears());

        ZoneId zoneId = ZoneId.of("America/Recife");
        ZoneOffset currentOffsetForMyZone = zoneId.getRules().getOffset(dataHoraNascimento);
        System.out.println("TZ" + currentOffsetForMyZone);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String format = formatter.format(dataHoraNascimento);
        System.out.println("Data de nascimento Formatada: " + format);


        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String format1 = formatter1.format(dataHoraNascimento);
        System.out.println("Data de nascimento (extenso): " + format1);

        System.out.println("Ano bissexto: " + dataHoraNascimento.toLocalDate().isLeapYear());

        System.out.println("Signo: " + signo(dataHoraNascimento.toLocalDate()));

        if (dataHoraNascimento.getYear() > 1976) {
            System.out.println("Ascendente: " + ascendente(signo(dataHoraNascimento.toLocalDate()), dataHoraNascimento.toLocalTime().minusHours(2)));
        } else if (dataHoraNascimento.getYear() > 1946 && dataHoraNascimento.getYear() < 1975) {
            System.out.println("Ascendente: " + ascendente(signo(dataHoraNascimento.toLocalDate()), dataHoraNascimento.toLocalTime().minusHours(1)));
        } else {
            System.out.println("Ascendente: " + ascendente(signo(dataHoraNascimento.toLocalDate()), dataHoraNascimento.toLocalTime()));
        }

        System.out.println("Signo Lunar: " + signoLunar(dataHoraNascimento.toLocalTime(), localNascimento));

    }

    public static String signo(LocalDate dataNascimento) {
        MonthDay monthDayNascimento = MonthDay.of(dataNascimento.getMonth(), dataNascimento.getDayOfMonth());

        MonthDay ariesStartDate = MonthDay.of(3, 21);
        MonthDay ariesEndDate = MonthDay.of(4, 20);

        MonthDay escorpiaoStartDate = MonthDay.of(10, 23);
        MonthDay escorpiaoEndDate = MonthDay.of(11, 21);

        MonthDay sagitarioStartDate = MonthDay.of(11, 22);
        MonthDay sagitarioEndDate = MonthDay.of(12, 21);

        if (isWithinRange(monthDayNascimento, ariesStartDate, ariesEndDate)) {
            return "Aries";
        } else if (isWithinRange(monthDayNascimento, escorpiaoStartDate, escorpiaoEndDate)) {
            return "Escorpiao";

        } else if (isWithinRange(monthDayNascimento, sagitarioStartDate, sagitarioEndDate)) {
            return "Sagitario";
        }

        return "Signo não encontrado ";
    }

    private static boolean isWithinRange(MonthDay dataNascimento, MonthDay startDate, MonthDay endDate) {
        return !(dataNascimento.isBefore(startDate) || dataNascimento.isAfter(endDate));
    }

    private static boolean isWithinRange(LocalTime horarioNAscimento, LocalTime startTime, LocalTime endTime) {
        return !(horarioNAscimento.isBefore(startTime) || horarioNAscimento.isAfter(endTime));
    }

    private static String ascendente(String signo, LocalTime horarioNascimento) {
        if ("Aries".equalsIgnoreCase(signo)) {
            if (isWithinRange(horarioNascimento, LocalTime.of(18, 31), LocalTime.of(20, 30))) {
                return "escorpião";
            }
        } else if ("Sagitario".equalsIgnoreCase(signo)) {
            if (isWithinRange(horarioNascimento, LocalTime.of(10, 31), LocalTime.of(12, 30))) {
                return "Peixes";
            }
        }

        return "Ascendente não encontrado ";
    }

    private static String signoLunar(LocalTime time, String localNascimento) {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        for (String s : zones) {
            if (s.contains(localNascimento)) {
                ZoneId zoneId = ZoneId.of(s);
                System.out.println(zoneId);

                if (zoneId.toString().equals("America/Recife") && time.isAfter(LocalTime.NOON)) {
                    return "Casimiro";
                }

                if (zoneId.toString().equals("America/Cuiaba") && time.isBefore(LocalTime.NOON)) {
                    return "Odin";
                }

                if (zoneId.toString().equals("America/Sao_Paulo")) {
                    return "Gandalf";
                }



            }
        }

        return "Em construcao";
    }
}


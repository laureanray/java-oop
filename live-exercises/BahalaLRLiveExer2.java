
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class BahalaLRLiveExer2 {
    public static final String[] tens = {
            "",
            "ten ",
            "twenty ",
            "thirty " ,
            "forty ",
            "fifty ",
            "sixty ",
            "seventy ",
            "eighty ",
            "ninety ",
    };

    public static final String[] ones = {
            "",
            "one ",
            "two ",
            "three ",
            "four ",
            "five ",
            "six ",
            "seven ",
            "eight ",
            "nine "
    };

    public static final String[] tens_edge = {
            "ten ",
            "eleven ",
            "twelve ",
            "thirteen ",
            "fourteen ",
            "fifteen ",
            "sixteen ",
            "seventeen ",
            "eighteen ",
            "nineteen "
    };

    public static void main(String[] args) throws Exception {
        boolean isValidName = false;
        boolean isValidDate = false;
        boolean isValidAmount = false;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Input Variables
        String name = "";
        Date InputDate;
        Double amount = 0d;

        Date CurrentDate = new Date();
        String inputDateString = "";

        while(isValidDate == false)
        {
            System.out.print("Date mm/dd/yyyy: ");
            try {
                inputDateString = reader.readLine();
                InputDate = new Date(inputDateString);
            }catch(IllegalArgumentException ex)
            {
                // Catch exception if the argument for Date constructor is invalid.
                System.out.println("Invalid date, please re-enter. ");
                continue;
            }

            if(CurrentDate.getDay() == InputDate.getDay() &&
                    CurrentDate.getMonth() == InputDate.getMonth() &&
                    CurrentDate.getYear() == InputDate.getYear())
            {
                isValidDate = true;
            }else{
                System.out.println("Invalid date, please re-enter. ");
            }
        }

        while(isValidName == false)
        {
            System.out.print("Enter name: ");
            name = reader.readLine();

            if(name.matches ("^[a-zA-Z. ]*$") && name.length() > 1){
                isValidName = true;
            }else{
                System.out.println("Invalid name, please re-enter.");
            }
        }

        String amountString = "";

        while(isValidAmount == false)
        {
            System.out.print("Enter amount: ");
            try {
                amount = Double.parseDouble(reader.readLine());
                amountString = " Pesos: **" + convert(amount) + "ONLY** ";
            } catch(Exception ex)
            {
                System.out.println("Invalid amount, please re-enter.");
                continue;
            }

            if(amount > 0 && amount <= 1000000.00)
            {
                isValidAmount = true;
            }
            else{
                System.out.println("Invalid amount, please re-enter.");
            }
        }


        String nameString = " Pay to the order of: " + name;

        for(int i = 0; i < (64 - (name.length() + 22)); i++)
        {
            nameString += " ";
        }

        nameString += "₱" + String.format("%.2f", amount);

        for(int i = 0; i < 81; i++)
        {
            System.out.printf("-");
        }

        System.out.println("\n\t\t\t\t\t\t\t\tDate: " + inputDateString);
        System.out.println(nameString);
        System.out.println(amountString);

        for(int i = 0; i < 81; i++)
        {
            System.out.printf("-");
        }
        
        System.out.println();


    }

    public static String convert(double amount)
    {
        boolean hundredThousands = false;
        boolean tenThousands = false;
        boolean ten = false;
        int amountInInteger = (int) amount;
        String centavosString = Double.toString(amount);
        String[] centavosArr = centavosString.split("\\.", 2);
        int centavos;
        if(centavosArr[1].length() == 1)
        {
            centavos = Integer.parseInt(centavosArr[1]) * 10;
        }else{
            centavos = Integer.parseInt(centavosArr[1]);
        }
        String result = "";
        if(amountInInteger / 1000000 != 0){
            result += (ones[amountInInteger/1000000] + "million ");
            amountInInteger -= 1000000;
        }

        if(amountInInteger / 100000 != 0){
            result += (ones[amountInInteger/100000] + "hundred ");
            hundredThousands = true;
            amountInInteger -= ((amountInInteger / 100000) * 100000);
        }

        if(amountInInteger / 10000 != 0)
        {
            if(amountInInteger / 10000 == 1)
            {
                amountInInteger -= ((amountInInteger / 10000) * 10000);
                result += (tens_edge[amountInInteger /1000]);
                amountInInteger -= ((amountInInteger /1000) * 1000);
                tenThousands = true;
            }else
            {
                result += (tens[amountInInteger /10000]);
                amountInInteger -= ((amountInInteger / 10000) * 10000);
            }
            hundredThousands = true;
        }

        if(amountInInteger / 1000 != 0 && !tenThousands)
        {
            result += (ones[amountInInteger / 1000]);
            amountInInteger -= ((amountInInteger / 1000) * 1000);
            hundredThousands = true;
        }

        if(hundredThousands)
        {
            result += "thousand ";
        }

        if(amountInInteger / 100 != 0)
        {
            result += (ones[amountInInteger / 100] + "hundred ");
            amountInInteger -= ((amountInInteger / 100) * 100);
        }

        if(amountInInteger / 10 != 0)
        {
            if(amountInInteger / 10 == 1)
            {
                amountInInteger -= ((amountInInteger / 10) * 10);
                result += (tens_edge[amountInInteger]);
                ten = true;
            }else
            {
                result += (tens[amountInInteger /10]);
                amountInInteger -= ((amountInInteger / 10) * 10);
            }
        }

        if(amount > 0 && !ten)
        {
            result += ones[amountInInteger];
        }

        result += "PESOS ";

        if(centavos > 0)
        {
            result += "AND ";
            if(centavos / 10 != 0)
            {
                if(centavos / 10 == 1)
                {
                    centavos -= ((centavos / 10) * 10);
                    result += (tens_edge[centavos]);
                    ten = true;
                }else
                {
                    result += (tens[centavos /10]);
                    centavos -= ((centavos / 10) * 10);
                }
            }

            if(amount > 0 && !ten)
            {
                result += ones[centavos];
            }

            if(centavos == 1)
            {
                result += "CENTAVO ";

            }else{
                result += "CENTAVOS ";

            }

        }

        return result.toUpperCase();

    }
}


package problema2;

import java.util.List;
import java.util.Scanner;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        OperatiiBD operatiiBD = (OperatiiBD) context.getBean("operatiiJDBC");
        System.out.println("-Adaugare in BD");
       // operatiiBD.insert(12, "Audi", "a6", "alb");
        System.out.println("-Afisarea masinilor din BD:");
        List<Masina> masini = operatiiBD.getListaMasini();
        //for (Masina m : masini) {
          //  System.out.println(m);
        //}
        masini.stream().forEach(System.out::println);
        System.out.println("-Dati nr matricol ");
        Scanner scanner=new Scanner(System.in);
        int nr_matricol=scanner.nextInt();
       // operatiiBD.delete(nr_matricol);


        System.out.print("- Afisarea datelor masinii cu nr matricol 12: " );
        Masina m = operatiiBD.getMasina(1);
        System.out.println(m);

            System.out.println("Numar masini cu marca Audi: ");
            System.out.println(operatiiBD.numar("Audi"));
    }
}


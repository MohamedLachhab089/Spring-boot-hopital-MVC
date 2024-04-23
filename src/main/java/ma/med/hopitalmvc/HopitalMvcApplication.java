package ma.med.hopitalmvc;

import ma.med.hopitalmvc.entities.Patient;
import ma.med.hopitalmvc.repositories.PatientRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HopitalMvcApplication {

    private PatientRepo patientRepo;

    public static void main(String[] args) {
        SpringApplication.run(HopitalMvcApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean stop it
    CommandLineRunner commandLineRunner(PatientRepo patientRepo) {
        return args -> {
            patientRepo.save(new Patient(null, "Mohamed", new Date(), false, 10));
            patientRepo.save(new Patient(null, "Hamza", new Date(), false, 10));
            patientRepo.save(new Patient(null, "Ayoub", new Date(), false, 10));

            patientRepo.findAll().forEach(s -> {
                System.out.println(s.getName());
            });

        };
    }
}

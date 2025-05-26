package com.bibliotecapersonale.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobLauncherController {

  private final JobLauncher jobLauncher;
  @Qualifier("scanAndSavePdfMetaJob")
  private final Job scanAndSavePdfMetaJob;

  public JobLauncherController(JobLauncher jobLauncher,
          @Qualifier("scanAndSavePdfMetaJob") Job job) {
    this.jobLauncher = jobLauncher;
    this.scanAndSavePdfMetaJob = job;
  }


  //todo creare metodo per controllare lo stato del job tramite l'id dello stesso e migliorare il tipo di risposta
  @GetMapping("/avvia")
  public ResponseEntity<String> avviaJob() {
    try {
      JobParameters params = new JobParametersBuilder()
              .addLong("startAt", System.currentTimeMillis())
              .toJobParameters();

      jobLauncher.run(scanAndSavePdfMetaJob, params);
      return ResponseEntity.ok("Job avviato correttamente.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'avvio del job: " + e.getMessage());
    }
  }
}

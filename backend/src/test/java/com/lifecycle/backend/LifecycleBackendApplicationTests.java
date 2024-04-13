package com.lifecycle.backend;

import com.lifecycle.backend.controller.ProcessController;
import com.lifecycle.backend.controller.StepController;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.Process;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LifecycleBackendApplicationTests {

	@Autowired
	private ProcessController processController;
	@Autowired
	private StepController stepController;

	@Test
	void stepCreation() {
		Step step = new Step("testStep", null, 1);
		System.out.println(step);
		ResponseEntity<Step> response = stepController.createStep(step);
		System.out.println("Step " + response.getBody().getStep_id() + " added to the database.");
		System.out.println("Received HTTP status " + response.getStatusCode());

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void processCreation() {
		Step step1 = new Step("processStep1", null, 1);
		Step step2 = new Step("processStep2", null, 1);
		ResponseEntity<Step> stepCreationResponse1 = stepController.createStep(step1);
		ResponseEntity<Step> stepCreationResponse2 = stepController.createStep(step2);
		List<Long> stepsToAdd = new ArrayList<>();
		stepsToAdd.add(stepCreationResponse1.getBody().getStep_id());
		stepsToAdd.add(stepCreationResponse2.getBody().getStep_id());

		Process process = new Process("testProcess", null);
		System.out.println("Created process object!");
		ResponseEntity<Process> response = processController.createProcess(process, stepsToAdd);

		System.out.println("Process " + response.getBody().getProcess_id() + " added to the database.");
		System.out.println("Received HTTP status " + response.getStatusCode());

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


}

package com.lifecycle.backend;

import com.lifecycle.backend.controller.ProcessController;
import com.lifecycle.backend.controller.StepController;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.StepInProcess;
import org.apache.coyote.Response;
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
	void processCRUD() {
		Step step1 = new Step("processStep1", null, 1);
		Step step2 = new Step("processStep2", null, 1);
		ResponseEntity<Step> stepCreationResponse1 = stepController.createStep(step1);
		ResponseEntity<Step> stepCreationResponse2 = stepController.createStep(step2);
		List<Long> stepsToAdd = new ArrayList<>();
		stepsToAdd.add(stepCreationResponse1.getBody().getStep_id());
		stepsToAdd.add(stepCreationResponse2.getBody().getStep_id());

		// entity creation
		Process process = new Process("testProcess", null);
		ResponseEntity<Process> response = processController.createProcess(process);
		System.out.println("Created process object!");
		System.out.println("Process " + process.getProcess_id() + " added to the database.");
		System.out.println("Received HTTP status " + response.getStatusCode());
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// steps addition
		System.out.println("Adding steps to process..." + response.getStatusCode());
		response = processController.updateStepsInProcess(process.getProcess_id(), stepsToAdd);
		System.out.println("Steps added to process, should show up in database!");
		System.out.println("First step list:");
		for (StepInProcess step : response.getBody().getSteps()) {
			System.out.println("ID: " + step.getStep().getStep_id() + ", " + "Position: " + step.getPosition());
		}
		System.out.println("Received HTTP status " + response.getStatusCode());
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// steps updating
		System.out.println("Switching the processes' positions and updating the process..." + response.getStatusCode());
		stepsToAdd = new ArrayList<>();
		stepsToAdd.add(stepCreationResponse2.getBody().getStep_id()); stepsToAdd.add(stepCreationResponse1.getBody().getStep_id());
		response = processController.updateStepsInProcess(process.getProcess_id(), stepsToAdd);
		System.out.println("Steps updated!");
		System.out.println("Second step list: ");
		for (StepInProcess step : response.getBody().getSteps()) {
			System.out.println("- ID: " + step.getStep().getStep_id() + ", " + "Position: " + step.getPosition());
		}
	 	System.out.println("Received HTTP status " + response.getStatusCode());
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// entity removal
		System.out.println("Deleting the process..." + response.getStatusCode());
		ResponseEntity<HttpStatus> finalResponse = processController.deleteProcess(process.getProcess_id());
		System.out.println("Cascading effect will remove StepInProcess entities connected to this process.");
		System.out.println("Received HTTP status " + finalResponse.getStatusCode());
		assertEquals(HttpStatus.OK, finalResponse.getStatusCode());
	}

}

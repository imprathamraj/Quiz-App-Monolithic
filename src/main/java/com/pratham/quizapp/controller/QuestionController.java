package com.pratham.quizapp.controller;

import com.pratham.quizapp.model.Question;
import com.pratham.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
	@Autowired
	QuestionService questionService;

	@GetMapping("allQuestions")
	public ResponseEntity< List<Question> > getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping("id/{id}")
	public ResponseEntity<Question> getQuestionsById(@PathVariable int id){
		return questionService.getQuestionsById(id);
	}

	@GetMapping("category/{category}")
	public ResponseEntity< List<Question> > getQuestionsByCategory(@PathVariable String category){
		return questionService.getQuestionsByCategory(category);
	}

	@GetMapping("difficultyLevel/{difficultyLevel}")
	public ResponseEntity< List<Question> > getQuestionsByDifficultyLevel(@PathVariable String difficultyLevel){
		return questionService.getQuestionsByDifficultyLevel(difficultyLevel);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question){
		return questionService.addQuestion(question);
	}

	@PutMapping("update/{id}")
	public ResponseEntity< String >updateQuestionById(@RequestBody Question question, @PathVariable int id){
		return questionService.updateQuestionById(question, id);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity <String >deleteQuestionById(@PathVariable int id){
		return questionService.deleteQuestionById(id);
	}
}

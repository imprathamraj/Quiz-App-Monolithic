package com.pratham.quizapp.service;

import com.pratham.quizapp.dao.QuestionDao;
import com.pratham.quizapp.dao.QuizDao;
import com.pratham.quizapp.model.Response;
import com.pratham.quizapp.model.Question;
import com.pratham.quizapp.model.QuestionWrapper;
import com.pratham.quizapp.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        try{
            List<Question> questions = questionDao.findNRandomQuestionsByCategory(numQ, category);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizDao.save(quiz);

            return new ResponseEntity<>("Successfully created quiz!", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create quiz!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        try{
            Optional<Quiz> quiz =  quizDao.findById(id);

            List<Question> questionsFromDB = quiz.get().getQuestions();

            List<QuestionWrapper> questionForUser = new ArrayList<>();
            for(Question q : questionsFromDB){
                QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                questionForUser.add(qw);
            }

            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> submitQuiz(int id, List<Response> responses) {
        try{
            Quiz quiz = quizDao.findById(id).get();
            List<Question> questions = quiz.getQuestions();
            int right=0;
            int index=0;
            for(Response r: responses){
                if(r.getResponse().equals(questions.get(index).getRightAnswer())){
                    right++;
                }
                index++;
            }
            return new ResponseEntity<>(right,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(0,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.hcl.doconnect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hcl.doconnect.exception.QuestionNotFoundException;
import com.hcl.doconnect.model.Question;
import com.hcl.doconnect.model.User;
import com.hcl.doconnect.repository.AnswerRepository;
import com.hcl.doconnect.repository.CommentRepository;
import com.hcl.doconnect.repository.QuestionRepository;
import com.hcl.doconnect.service.QuestionService;

@SpringBootTest
public class QuestionServiceTest {
 
	@MockBean
	private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private QuestionService questionService;
 
 
    @Test
     void testFindById() throws QuestionNotFoundException {
        // Given
        Long id = 1L;
        Question question = new Question();
        question.setId(id);
        when(questionRepository.findById(id)).thenReturn(Optional.of(question));
 
        // When
        Question foundQuestion = questionService.findById(id);
 
        // Then
        assertEquals(id, foundQuestion.getId());
    }
    
    @Test
     void testFindById_NotFound() {
        // Given
        Long id = 1L;
        when(questionRepository.findById(id)).thenReturn(Optional.empty());
 
        // When, Then
        assertThrows(QuestionNotFoundException.class, () -> questionService.findById(id));
    }
    
    @Test
    public void testFindAll() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", "Content 1", new User(),"APPROVED"));
        questions.add(new Question("Question 2", "Content 2", new User(),"APPROVED"));
        
        when(questionRepository.findAll()).thenReturn(questions);
 
        List<Question> result = questionService.findAll();
 
        assertEquals(2, result.size());
        assertEquals("Question 1", result.get(0).getTopic());
        assertEquals("Content 2", result.get(1).getContent());
    }
 
    @Test
    public void testSaveQuestion() {
        Question question = new Question("Test Title", "Test Content", new User(),"APPROVED");
        
        when(questionRepository.save(any(Question.class))).thenReturn(question);
 
        Question savedQuestion = questionService.saveQuestion(question);
 
        assertEquals("Test Title", savedQuestion.getTopic());
        assertEquals("Test Content", savedQuestion.getContent());
    }
 
    @Test
    public void testSearchQuestions() {
        String searchString = "test";
        List<Question> approvedQuestions = new ArrayList<>();
        approvedQuestions.add(new Question("Test Title 1", "Test Content 1", new User(),"APPROVED"));
        approvedQuestions.add(new Question("Test Title 2", "Test Content 2", new User(),"APPROVED"));
        
        when(questionRepository.findByContentContainingIgnoreCaseAndStatus(searchString, "APPROVED"))
                .thenReturn(approvedQuestions);
 
        List<Question> result = questionService.searchQuestions(searchString);
 
        assertEquals(2, result.size());
        assertEquals("Test Title 1", result.get(0).getTopic());
        assertEquals("Test Content 2", result.get(1).getContent());
    }
    
    
}


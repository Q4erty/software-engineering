package org.narxoz.lab5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final ApplicationRequestRepository requestRepository;
    private final CoursesRepository coursesRepository;
    private final OperatorsRepository operatorsRepository;

    public List<ApplicationRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public void createRequest(ApplicationRequest request, Long courseId) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден с id: " + courseId));
        request.setCourse(course);
        request.setHandled(false);
        request.setOperators(new ArrayList<>());
        requestRepository.save(request);
    }

    public ApplicationRequest getRequest(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена с id: " + id));
    }

    public List<Operators> getAllOperators() {
        return operatorsRepository.findAll();
    }

    public void assignOperators(Long requestId, List<Long> operatorIds) {
        ApplicationRequest request = getRequest(requestId);
        if (request.isHandled()) {
            throw new IllegalStateException("Заявка уже обработана");
        }

        List<Operators> operators = operatorsRepository.findAllById(operatorIds);
        request.setOperators(operators);
        request.setHandled(true);
        requestRepository.save(request);
    }

    public void deleteOperatorFromRequest(Long requestId, Long operatorId) {
        ApplicationRequest request = getRequest(requestId);
        request.getOperators().removeIf(op -> op.getId().equals(operatorId));

        if (request.getOperators().isEmpty()) {
            request.setHandled(false);
        }

        requestRepository.save(request);
    }

}


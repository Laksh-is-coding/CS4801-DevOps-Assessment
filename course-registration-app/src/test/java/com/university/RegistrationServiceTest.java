package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }
    
    @Test
    void shouldRejectRegistrationWhenCourseIsAtCapacity() {
        Student student = new Student("S102", "Rahul");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        // Fill the course to capacity.
        for (int i = 0; i < course.getCapacity(); i++) {
            course.incrementEnrollment();
        }

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
        assertEquals(0, student.getTotalCredits());
        assertEquals(30, course.getCurrentEnrollment());
    }

    @Test
    void shouldAllowRegistrationWhenOneSeatRemains() {
        Student student = new Student("S103", "Priya");

        Course course = new Course(
                "CS302",
                "Database Systems",
                4,
                30,
                true,
                null
        );

        // Fill all but one seat.
        for (int i = 0; i < course.getCapacity() - 1; i++) {
            course.incrementEnrollment();
        }

        RegistrationService service = new RegistrationService();
        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS302"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(30, course.getCurrentEnrollment());
    }
    
    @Test
    void shouldAcceptCS3A1AsEquivalentToCS301Prerequisite() {
        Student student = new Student("S104", "Arun");
        student.addCompletedPrerequisite("CS3A1");

        Course course = new Course(
                "CS401",
                "Advanced Computing",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));
        assertTrue(student.isRegistered("CS401"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectCS301PrerequisiteWhenNeitherCodeWasCompleted() {
        Student student = new Student("S105", "Meera");

        Course course = new Course(
                "CS401",
                "Advanced Computing",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        assertFalse(service.register(student, course));
        assertFalse(student.isRegistered("CS401"));
        assertEquals(0, student.getTotalCredits());
        assertEquals(0, course.getCurrentEnrollment());
    }

    @Test
    void shouldKeepCS301AndCS3A1DistinctForDuplicateRegistration() {
        Student student = new Student("S106", "Kiran");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));
        assertFalse(service.register(student, course));

        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

}
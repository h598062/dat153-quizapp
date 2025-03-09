# dat153-quizapp

## Tests

We currently have two tests, MainActivityTest and QuizLiveDataTest. There is a planned third test,
GalleryActivityTest, but we have not found the time for it yet, due to problems with the
QuizLiveDataTest.

### MainActivityTest

This test checks that clicking the buttons in main menu navigates to the correct new activity
The expected result is for the "start" button to navigate to the QuizActivity, and the "Gallery"
button to navigate to the GalleryActivity.
This test correctly passes.

### QuizLiveDataTest

This test is a precursor test for checking for whether the score correctly updates during the quiz.
The main issue that it handles is that the button order is random, and we need the correct button to
click and check if the score updates correctly. Checking for correct score updates depends on this
to be working, so we can properly check fail vs correct answers etc.
The problem is making sure that we only try to get the correct button after it is correctly loaded,
due to the image and such being loaded async from the room database.
So far, we can get it working sometimes, but there is a race condition in how we load from the room
database and more often than not, something loads before it should and nothing works.
We think the problem is in how we have made the viewmodel and activity together with a fragment.
To fix this, we think we might just have to re-create the entire quiz activity and rethink how the
dataflow should go.
Due to spending too much time on this particular issue, with several failed refactorings to fix it,
we have not been able to create all expected test cases.

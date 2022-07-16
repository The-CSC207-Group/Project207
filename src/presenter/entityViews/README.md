Here stored are entity views.

In the context of the terminal, view methods here return a string while making sure the last character they return isn't
the newline char as this is already done automatically when calling println.

View methods here should not be called from controllers but rather should be called from the screenViews.

*Why?* I'm glad you asked.

In order to adhere to the Single Responsibility Principle, both a doctor and a patient may need to view prescriptions,
therefore I used the extract class refactoring method to extract the common behavior to viewing a prescription.

Here stored are responses from the `ScreenViews`.

We create a new response class when we want to return more than one item/variables. Unless it makes 100% sense to do so,
using a response class instead of a List creates a much better interface for controller code to use.

# Why response classes

For example, imagine reading this code:

```java
public class Main {
    public static void main() {

        List<String> x = new SignInView().askForLoginDetails();
        new SystemAccess().patientSignIn(x.get(0), x.get(1));

    }
}
```

What is `x.get(0)` and `x.get(1)`?

No way of knowing without looking at the implementation of presenters!

Now consider this code:

```java
import presenter.response.UserCredentials;
import useCases.accessClasses.SystemAccess;

public class Main {
    public static void main() {

        UserCredentials x = new SignInView().askForLoginDetails();
        new SystemAccess().patientSignIn(x.username(), x.password());

    }
}
```

Much better!

# How to create a response using intellij

First create a record of all the values you need:

```java
public record LoginCreds(String username, String password){}
```

Then right click and show context actions and then convert it to a class. Voilà!

The reason we convert it to a class is that records are not implemented in java 11.

package com.theakatsuki.hiredevelopers;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class LoginTesting {

    @Test
    public void logingUserTesting(){
        FirebaseAuth auth = Mockito.mock(FirebaseAuth.class);
        final Task<AuthResult> mockedAuth = Mockito.mock(Task.class);
        when(auth.signInWithEmailAndPassword("hireDeveloperTest@gmail.com","password"))
                .thenReturn(mockedAuth);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Task<AuthResult> authResult = invocation.getArgument(0,Task.class);
                assertEquals(true,authResult.isSuccessful());
                return null;
            }
        });

    }

}
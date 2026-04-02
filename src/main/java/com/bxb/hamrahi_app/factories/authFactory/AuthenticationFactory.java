package com.bxb.hamrahi_app.factories.authFactory;

import com.bxb.hamrahi_app.util.AuthenticationRequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory class to handle operations related to authentication requests.
 * This class implements the AuthenticationOperation interface
 * and provides implementations for the defined methods.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFactory {

    /** List of operations that can be performed on authentication requests. */
    private final List<AuthenticationOperation> operationList;

    /** Map to associate request types with their corresponding operations. */
    private final Map<AuthenticationRequestType,
            AuthenticationOperation> operationMap = new HashMap<>();

    /**
     * Method for initializing the operation map after the bean is constructed.
     * It populates the map with the available operations,
     * based on their request types.
     */
    @PostConstruct
    private void putValues(){
        operationList.forEach(
                list -> operationMap
                        .put(list.getAuthenticationRequestType(),list));
    }

    /**
     * Method to perform the operation based on the request type.
     * It retrieves the appropriate operation from the map and executes it.
     *
     * @param requestType the type of the request indicating which operation to
     *                    perform.
     * @return AuthenticationOperation containing the result of the
     * operation.
     */
    public AuthenticationOperation getOperation(
            final AuthenticationRequestType requestType){
        return operationMap.get(requestType);
    }
}

package com.selflearning.blogging.bloggingapplicationapi.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    String resourceName;
    String fieldName;
    long fieldValue;
    String stringFieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue)
    {
        super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String stringFieldValue)
    {
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.stringFieldValue = stringFieldValue;
    }
}

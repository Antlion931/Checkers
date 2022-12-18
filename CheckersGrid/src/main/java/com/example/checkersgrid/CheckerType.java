package com.example.checkersgrid;

public enum CheckerType
{
    WHITE(1), BLACK(-1);

    final int directionOfMovement;

    CheckerType(int direction)
    {
        directionOfMovement = direction;
    }
}

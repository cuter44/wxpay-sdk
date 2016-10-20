package com.github.cuter44.wxmsg.constants;

public enum EventType
{
  // PSEUDO
    // Event reuse these channel of msg, below types are no need.
    ///** Handlers register as preprocess will be invoked sequentially in spite of return state.
     //* This functionality depends on implementors.
     //*/
    //PREPROCESS(),
    ///** Handlers register as postprocess will be invoked sequentially in spite of return state.
     //* This functionality depends on implementors.
     //*/
    //POSTPROCESS(),
    ///** Handlers register as fallback will be invoked when none of the standard handlers returns true, and stop at once one of them returns true.
     //* This functionality depends on implementors.
     //*/
    //FALLBACK(),

    //ECHO(),
    /** Event that has not yet been encapsulated will be in type of unknown, treated as a normal type.
     */
    UNKNOWN(),

  // STANDARD
    subscribe(),
    unsubscribe(),
    SCAN(),
    LOCATION(),
    CLICK(),
    VIEW();

    private EventType()
    {
        return;
    }


}

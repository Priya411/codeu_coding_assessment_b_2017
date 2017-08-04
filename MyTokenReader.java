// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.codeu.mathlang.parsing.TokenReader;
import com.google.codeu.mathlang.core.tokens.*;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {
  public ArrayList<String> splitSource;

  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    splitSourceToArray(source);
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
    for(String string: splitSource)
    {
      System.out.println(string);
    }
    if(splitSource.size()==0 || (splitSource.get(0).equals(";")) &&splitSource.size()==1){
      System.out.println("reached end");
      return null;
    }
    if ((splitSource.get(0).equals(";")||splitSource.get(0).equals(" ")) &&splitSource.size()>1)
    {
      System.out.println("reached middle");
      splitSource.remove(0);
      next();
    }
    if(splitSource.get(0).indexOf(" ")!=splitSource.get(0).length()-1)
    {
      System.out.println("reached string");
      String token = splitSource.get(0);
      splitSource.remove(0);
      return new StringToken(token);
    }
    if(splitSource.get(0).length()>1)
    {
      String token = splitSource.get(0);
      splitSource.remove(0);
      if(Character.isLetter(token.charAt(0))) {
        System.out.println("reached name");
        return new NameToken(token);
      }
      else {
        System.out.println("reached string");
        return new StringToken(token);
      }
    }
    if(splitSource.get(0).length()==1)
    {
      String token = splitSource.get(0);
      splitSource.remove(0);
      if(Character.isDigit(token.charAt(0))) {
        System.out.println("reached number");
        return new NumberToken(Double.parseDouble(token));
      }
      else {
        System.out.println("reached synbol");
        return new SymbolToken(token.charAt(0));
      }
    }
    return null;
  }

  public void splitSourceToArray(String source)
  {
    //This function will split the input source into any arrayList of the tokens
    // NameToken: No white space, starts with alphabetical character
    // NumberToken: Floating point numerical token
    // StringToken: String token, no restrictions on character types
    // SymbolToken: Single character token used by the parser
    int indexSplit = -1;
    splitSource = new ArrayList<>(Arrays.asList(source.split("\"")));
    for(String string: splitSource)
    {
      System.out.println(string);
    }
  }
}

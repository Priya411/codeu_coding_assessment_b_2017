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

import com.google.codeu.mathlang.core.tokens.*;
import com.google.codeu.mathlang.parsing.TokenReader;

import javax.naming.Name;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  String source;
  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
    this.source=source;
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.
    source = source.trim();
    return getToken();
  }

  public boolean isSymbol(char inpChar)
  {
    return (inpChar==';' || inpChar=='+' || inpChar=='-' || inpChar=='*' || inpChar=='/' || inpChar=='=');
  }
  public Token getToken() throws IOException
  {
    if(source.length()==0)
    {
      return null;
    }
    char firstChar = source.charAt(0);
    if(Character.isDigit(firstChar))
    {
      return getNumber();
    }
    else if(Character.isLetter(firstChar))
    {
      return getName();
    }
    else if(firstChar=='\"')
    {
      return getStringTok();
    }
    else if(isSymbol(firstChar))
    {
      return getSymbol();
    }
    throw new IOException();

  }

  public NameToken getName()
  {
    int stop = 0;
    while(Character.isLetter(source.charAt(stop)))
    {
      stop++;
    }
    String name = source.substring(0,stop);
    name.replaceAll(" ", "");
    source = source.substring(stop);
    return new NameToken(name);
  }

  public NumberToken getNumber()
  {
    int index = 0;
    while(index<source.length()&&Character.isDigit(source.charAt(index)))
    {
      index++;
    }
    if(index<source.length()&&source.charAt(index)=='.')
    {
      index++;
    }
    while(index<source.length()&&Character.isDigit(source.charAt(index)))
    {
      index++;
    }
    String number = source.substring(0,index);
    source = source.substring(index);
    return new NumberToken(Double.parseDouble(number));
  }

  public StringToken getStringTok() throws IOException
  {
    int stop = source.indexOf('\"', source.indexOf('\"') + 1);
    if(stop==-1)
    {
      throw new IOException();
    }
    String string = source.substring(1,stop);
    source = source.substring(stop+1);
    return new StringToken(string);
  }

  public SymbolToken getSymbol()
  {
    char symbol = source.charAt(0);
    source = source.substring(1);
    return new SymbolToken(symbol);
  }
}
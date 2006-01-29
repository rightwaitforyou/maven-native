package org.codehaus.mojo.natives.bcc;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

import org.codehaus.mojo.natives.NativeBuildException;
import org.codehaus.mojo.natives.c.CLinker;
import org.codehaus.mojo.natives.linker.LinkerConfiguration;

import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.Commandline;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:dantran@gmail.com">Dan Tran</a>
 * @version $Id$
 */
public class BCCLinker 
    extends CLinker
{
	
    public static final String DEFAULT_EXECUTABLE = "ilink32";
    
    protected Commandline createLinkerCommandLine( List objectFiles, LinkerConfiguration config )
        throws NativeBuildException
    {
        Commandline cl = new Commandline();
    
        cl.setWorkingDirectory( config.getWorkingDirectory().getPath() );

        String executable = DEFAULT_EXECUTABLE;
    
        /**
         *Turbo Incremental Link 5.68 Copyright (c) 1997-2005 Borland
         *Syntax: ILINK32 options objfiles, exefile, mapfile, libfiles, deffile, resfiles        
         * 
         */
        if ( config.getExecutable() != null && config.getExecutable().trim().length() != 0 )
        {
            executable = config.getExecutable();
        }
    
        cl.createArgument().setValue( executable );
    
        cl.addArguments( config.getStartOptions() );
        
        //objfiles
        for ( int i = 0; i < objectFiles.size(); ++i )
        {
            File objFile = (File) objectFiles.get(i);

            cl.createArgument().setValue( objFile.getPath() );
        }

        File [] externalLibs = config.getExternalLibraries();
        
        for ( int i = 0; i < externalLibs.length; ++i )
        {
            if ( ! FileUtils.getExtension( externalLibs[i].getPath() ).toLowerCase().equals( "res" ) )
            {
                cl.createArgument().setValue( externalLibs[i].getPath() );
            }
        }
        
        //ouput file
        cl.createArgument().setValue( "," + config.getOutputFilePath() );

        //map files + system lib, and def file to be given by user in middle options
        //  a comma is required between map, lib, and def 
        cl.createArgument().setValue( "," );
        cl.addArguments( config.getMiddleOptions() );
        
        //res file
        cl.createArgument().setValue( "," );
        for ( int i = 0; i < externalLibs.length; ++i )
        {
            if ( FileUtils.getExtension( externalLibs[i].getPath() ).toLowerCase().equals( "res" ) )
            {
                cl.createArgument().setValue( externalLibs[i].getPath() );
            }
        }
        
        this.setupCommandlineEnv( cl, config );
        
        return cl;
    }
}
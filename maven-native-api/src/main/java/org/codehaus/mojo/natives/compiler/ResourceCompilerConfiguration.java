package org.codehaus.mojo.natives.compiler;

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

import java.io.File;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author <a href="mailto:dantran@gmail.com">Dan Tran</a>
 * @version $Id$
 */

public class ResourceCompilerConfiguration
{
    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private File providerHome;
	
	private File baseDir;
	
	private File outputDirectory;

    private File debugOutputDirectory;

    private String [] options = EMPTY_STRING_ARRAY;
    
    private File [] includePaths = EMPTY_FILE_ARRAY;
    
    private File [] systemIncludePaths = EMPTY_FILE_ARRAY;
    
	public ResourceCompilerConfiguration ()
	{
	}
		
	public File getBaseDir()
	{
		return this.baseDir;
	}
	
	public void setBaseDir( File basedir )
	{
		this.baseDir = basedir;
	}
	
	public String [] getOptions()
	{
		return this.options;
	}

	public void setOptions( String [] options )
	{
        this.options = options;

        if ( this.options == null ) 
        {
            this.options = EMPTY_STRING_ARRAY;
        }
	}

	public File getProviderHome()
	{
		return this.providerHome;
	}
	
	public void setProviderHome( File dir )
	{
		this.providerHome = dir;
	}
    
    public File getOutputDirectory()
    {
        return this.outputDirectory;
    }
    
    public void setOutputDirectory( File dir )
    {
        this.outputDirectory = dir;
    }

    public File getDebugOutputDirectory()
    {
        return this.debugOutputDirectory;
    }
    
    public void setDebugOutputDirectory( File dir )
    {
        this.debugOutputDirectory = dir;
    }
    
    public File [] getIncludePaths()
    {
        return this.includePaths;
    }
    
    public void setIncludePaths( File [] paths )
    {
        this.includePaths = paths;
        
        if ( this.includePaths == null )
        {
            this.includePaths = EMPTY_FILE_ARRAY;
        }
    }

    public File [] getSystemIncludePaths()
    {
        return this.systemIncludePaths;
    }
    
    public void setSystemIncludePaths( File [] paths )
    {
        this.systemIncludePaths = paths;
        
        if ( this.systemIncludePaths == null )
        {
            this.systemIncludePaths = EMPTY_FILE_ARRAY;
        }
    }
    
    ////////////////////////////////////////////////////////////
    //                           HELPER
    ///////////////////////////////////////////////////////////
    public File getOutputFile ( File src ) 
    {
        String srcPath = src.getPath();
        
        String destPath = this.getOutputDirectory().getPath() + "/" + 
                            FileUtils.basename( srcPath ) + "res";

        return new File ( destPath );
    }
    
}

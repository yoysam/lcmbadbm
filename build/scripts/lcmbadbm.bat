@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  lcmbadbm startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and LCMBADBM_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\lcmbadbm.jar;%APP_HOME%\lib\jfreechart-1.0.17.jar;%APP_HOME%\lib\javax.persistence-api-2.2.jar;%APP_HOME%\lib\guava-28.0-jre.jar;%APP_HOME%\lib\org.eclipse.persistence.jpa.modelgen-2.4.2.jar;%APP_HOME%\lib\eclipselink-2.7.4.jar;%APP_HOME%\lib\derby-10.13.1.1.jar;%APP_HOME%\lib\jcommon-1.0.21.jar;%APP_HOME%\lib\xml-apis-1.3.04.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-2.8.1.jar;%APP_HOME%\lib\error_prone_annotations-2.3.2.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.17.jar;%APP_HOME%\lib\org.eclipse.persistence.jpa-2.4.2.jar;%APP_HOME%\lib\org.eclipse.persistence.core-2.4.2.jar;%APP_HOME%\lib\jakarta.persistence-2.2.2.jar;%APP_HOME%\lib\commonj.sdo-2.1.1.jar;%APP_HOME%\lib\org.eclipse.persistence.asm-2.4.2.jar;%APP_HOME%\lib\javax.persistence-2.0.5.jar;%APP_HOME%\lib\org.eclipse.persistence.antlr-2.4.2.jar;%APP_HOME%\lib\org.eclipse.persistence.jpa.jpql-2.4.2.jar

@rem Execute lcmbadbm
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %LCMBADBM_OPTS%  -classpath "%CLASSPATH%" App %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable LCMBADBM_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%LCMBADBM_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

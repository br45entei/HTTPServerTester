/*******************************************************************************
 * 
 * Copyright © 2022 Brian Reid (br45entei@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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
 * 
 *******************************************************************************/
package com.gmail.br45entei.http.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/** @author Brian Reid &lt;br45entei&#064;gmail.com&gt;
 * @see #getMimeTypeForExtension(String)
 * @see #getMimeType(File, String) */
public class MimeTypes {
	
	/** The default MIME type to use when no other MIME type is known. */
	public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
	
	/** A hash map containing common mime types and their associated file
	 * extensions */
	public static final HashMap<String, String> MIME_Types = new HashMap<>();
	
	private static final HashMap<String, String> VLC_MIME_Types = new HashMap<>();
	
	static {
		
		// TODO Go through and update all existing entries (and add missing ones) to match the standard MIME types from IANA:
		// https://www.iana.org/assignments/media-types/media-types.xhtml
		
		MIME_Types.put(".$323", "text/h323");
		MIME_Types.put(".$3gp", "video/3gpp");
		MIME_Types.put(".$7z", "application/x-7z-compressed");
		MIME_Types.put(".7z", "application/x-7z-compressed");
		MIME_Types.put(".abw", "application/x-abiword");
		MIME_Types.put(".ace", "application/x-ace-compressed");
		MIME_Types.put(".ai", "application/postscript");
		MIME_Types.put(".aif", "audio/x-aiff");
		MIME_Types.put(".aifc", "audio/x-aiff");
		MIME_Types.put(".aiff", "audio/x-aiff");
		MIME_Types.put(".alc", "chemical/x-alchemy");
		MIME_Types.put(".art", "image/x-jg");
		MIME_Types.put(".asc", "text/plain");
		MIME_Types.put(".asf", "video/x-ms-asf");
		MIME_Types.put(".$asn", "chemical/x-ncbi-asn1");
		MIME_Types.put(".asn", "chemical/x-ncbi-asn1-spec");
		MIME_Types.put(".aso", "chemical/x-ncbi-asn1-binary");
		MIME_Types.put(".asx", "video/x-ms-asf");
		MIME_Types.put(".asp", "application/asp");
		MIME_Types.put(".aspx", "application/aspx");
		MIME_Types.put(".atom", "application/atom");
		MIME_Types.put(".atomcat", "application/atomcat+xml");
		MIME_Types.put(".atomsrv", "application/atomserv+xml");
		MIME_Types.put(".au", "audio/basic");
		MIME_Types.put(".aup", "application/x-audacity");
		MIME_Types.put(".avi", "video/x-msvideo");
		MIME_Types.put(".bak", "application/x-trash");
		MIME_Types.put(".bat", "application/x-msdos-program");
		MIME_Types.put(".b", "chemical/x-molconn-Z");
		MIME_Types.put(".bcpio", "application/x-bcpio");
		MIME_Types.put(".bib", "text/x-bibtex");
		MIME_Types.put(".bin", "application/x-binary");
		MIME_Types.put(".bmp", "image/x-ms-bmp");
		MIME_Types.put(".book", "application/x-maker");
		MIME_Types.put(".boo", "text/x-boo");
		MIME_Types.put(".bsd", "chemical/x-crossfire");
		MIME_Types.put(".c3d", "chemical/x-chem3d");
		MIME_Types.put(".cab", "application/vnd.ms-cab-compressed");//"application/x-cab");
		MIME_Types.put(".cac", "chemical/x-cache");
		MIME_Types.put(".cache", "chemical/x-cache");
		MIME_Types.put(".cap", "application/cap");
		MIME_Types.put(".cascii", "chemical/x-cactvs-binary");
		MIME_Types.put(".cat", "application/vnd.ms-pki.seccat");
		MIME_Types.put(".cbin", "chemical/x-cactvs-binary");
		MIME_Types.put(".cbr", "application/x-cbr");
		MIME_Types.put(".cbz", "application/x-cbz");
		MIME_Types.put(".cc", "text/x-c++src");
		MIME_Types.put(".cdf", "application/x-cdf");
		MIME_Types.put(".cdr", "image/x-coreldraw");
		MIME_Types.put(".cdt", "image/x-coreldrawtemplate");
		MIME_Types.put(".cdx", "chemical/x-cdx");
		MIME_Types.put(".cdy", "application/vnd.cinderella");
		MIME_Types.put(".cef", "chemical/x-cxf");
		MIME_Types.put(".cer", "chemical/x-cerius");
		MIME_Types.put(".chm", "chemical/x-chemdraw");
		MIME_Types.put(".chrt", "application/x-kchart");
		MIME_Types.put(".cif", "chemical/x-cif");
		MIME_Types.put(".$class", "application/java-vm");
		MIME_Types.put(".class", "application/java-vm");
		MIME_Types.put(".cls", "text/x-tex");
		MIME_Types.put(".cmdf", "chemical/x-cmdf");
		MIME_Types.put(".cml", "chemical/x-cml");
		MIME_Types.put(".cod", "application/vnd.rim.cod");
		MIME_Types.put(".collection", "font/collection");
		MIME_Types.put(".com", "application/x-msdos-program");
		MIME_Types.put(".cpa", "chemical/x-compass");
		MIME_Types.put(".cpio", "application/x-cpio");
		MIME_Types.put(".cpp", "text/x-c++src");
		MIME_Types.put(".$cpt", "application/mac-compactpro");
		MIME_Types.put(".cpt", "image/x-corelphotopaint");
		MIME_Types.put(".crl", "application/x-pkcs7-crl");
		MIME_Types.put(".crt", "application/x-x509-ca-cert");
		MIME_Types.put(".csf", "chemical/x-cache-csf");
		MIME_Types.put(".$csh", "application/x-csh");
		MIME_Types.put(".csh", "text/x-csh");
		MIME_Types.put(".csm", "chemical/x-csml");
		MIME_Types.put(".csml", "chemical/x-csml");
		MIME_Types.put(".css", "text/css");
		MIME_Types.put(".csv", "text/csv");
		MIME_Types.put(".ctab", "chemical/x-cactvs-binary");
		MIME_Types.put(".c", "text/x-csrc");
		MIME_Types.put(".ctx", "chemical/x-ctx");
		MIME_Types.put(".cu", "application/cu-seeme");
		MIME_Types.put(".cub", "chemical/x-gaussian-cube");
		MIME_Types.put(".cxf", "chemical/x-cxf");
		MIME_Types.put(".cxx", "text/x-c++src");
		MIME_Types.put(".dat", "chemical/x-mopac-input");
		MIME_Types.put(".db", "application/x-database");
		MIME_Types.put(".dcr", "application/x-director");
		MIME_Types.put(".deb", "application/x-debian-package");
		MIME_Types.put(".diff", "text/x-diff");
		MIME_Types.put(".dif", "video/dv");
		MIME_Types.put(".dir", "application/x-director");
		MIME_Types.put(".djv", "image/vnd.djvu");
		MIME_Types.put(".djvu", "image/vnd.djvu");
		MIME_Types.put(".dll", "application/x-msdos-program");
		MIME_Types.put(".dl", "video/dl");
		MIME_Types.put(".dmg", "application/x-apple-diskimage");
		MIME_Types.put(".dms", "application/x-dms");
		MIME_Types.put(".doc", "application/msword");
		MIME_Types.put(".dot", "application/msword");
		MIME_Types.put(".d", "text/x-dsrc");
		MIME_Types.put(".dvi", "application/x-dvi");
		MIME_Types.put(".dv", "video/dv");
		MIME_Types.put(".dx", "chemical/x-jcamp-dx");
		MIME_Types.put(".dxr", "application/x-director");
		MIME_Types.put(".emb", "chemical/x-embl-dl-nucleotide");
		MIME_Types.put(".embl", "chemical/x-embl-dl-nucleotide");
		MIME_Types.put(".eml", "message/rfc822");
		MIME_Types.put(".$ent", "chemical/x-ncbi-asn1-ascii");
		MIME_Types.put(".ent", "chemical/x-pdb");
		MIME_Types.put(".eot", "application/vnd.ms-fontobject");
		MIME_Types.put(".eps", "application/postscript");
		MIME_Types.put(".epub", "application/epub+zip");
		MIME_Types.put(".etx", "text/x-setext");
		MIME_Types.put(".exe", "application/x-msdos-program");
		MIME_Types.put(".ez", "application/andrew-inset");
		MIME_Types.put(".fb", "application/x-maker");
		MIME_Types.put(".fbdoc", "application/x-maker");
		MIME_Types.put(".fch", "chemical/x-gaussian-checkpoint");
		MIME_Types.put(".fchk", "chemical/x-gaussian-checkpoint");
		MIME_Types.put(".fig", "application/x-xfig");
		MIME_Types.put(".flac", "audio/x-flac");//"application/x-flac");
		MIME_Types.put(".fli", "video/fli");
		MIME_Types.put(".flv", "video/x-flv");
		MIME_Types.put(".fm", "application/x-maker");
		MIME_Types.put(".frame", "application/x-maker");
		MIME_Types.put(".frm", "application/x-maker");
		MIME_Types.put(".gal", "chemical/x-gaussian-log");
		MIME_Types.put(".gam", "chemical/x-gamess-input");
		MIME_Types.put(".gamin", "chemical/x-gamess-input");
		MIME_Types.put(".gau", "chemical/x-gaussian-input");
		MIME_Types.put(".gcd", "text/x-pcs-gcd");
		MIME_Types.put(".gcf", "application/x-graphing-calculator");
		MIME_Types.put(".gcg", "chemical/x-gcg8-sequence");
		MIME_Types.put(".gen", "chemical/x-genbank");
		MIME_Types.put(".gf", "application/x-tex-gf");
		MIME_Types.put(".gif", "image/gif");
		MIME_Types.put(".gitignore", "text/plain");
		MIME_Types.put(".gjc", "chemical/x-gaussian-input");
		MIME_Types.put(".gjf", "chemical/x-gaussian-input");
		MIME_Types.put(".gl", "video/gl");
		MIME_Types.put(".gnumeric", "application/x-gnumeric");
		MIME_Types.put(".gpt", "chemical/x-mopac-graph");
		MIME_Types.put(".gsf", "application/x-font");
		MIME_Types.put(".gsm", "audio/x-gsm");
		MIME_Types.put(".gtar", "application/x-gtar");
		MIME_Types.put(".hdf", "application/x-hdf");
		MIME_Types.put(".hh", "text/x-c++hdr");
		MIME_Types.put(".hin", "chemical/x-hin");
		MIME_Types.put(".hpp", "text/x-c++hdr");
		MIME_Types.put(".hqx", "application/mac-binhex40");
		MIME_Types.put(".hs", "text/x-haskell");
		MIME_Types.put(".hta", "application/hta");
		MIME_Types.put(".htc", "text/x-component");
		MIME_Types.put(".http", "message/http");
		MIME_Types.put(".$h", "text/x-chdr");
		MIME_Types.put(".html", "text/html");
		MIME_Types.put(".htm", "text/html");
		MIME_Types.put(".hxx", "text/x-c++hdr");
		MIME_Types.put(".ica", "application/x-ica");
		MIME_Types.put(".ice", "x-conference/x-cooltalk");
		MIME_Types.put(".ico", "image/x-icon");
		MIME_Types.put(".ics", "text/calendar");
		MIME_Types.put(".icz", "text/calendar");
		MIME_Types.put(".ief", "image/ief");
		MIME_Types.put(".iges", "model/iges");
		MIME_Types.put(".igs", "model/iges");
		MIME_Types.put(".iii", "application/x-iphone");
		MIME_Types.put(".ini", "text/plain");
		MIME_Types.put(".inp", "chemical/x-gamess-input");
		MIME_Types.put(".ins", "application/x-internet-signup");
		MIME_Types.put(".iso", "application/x-iso9660-image");
		MIME_Types.put(".isp", "application/x-internet-signup");
		MIME_Types.put(".ist", "chemical/x-isostar");
		MIME_Types.put(".istr", "chemical/x-isostar");
		MIME_Types.put(".jad", "text/vnd.sun.j2me.app-descriptor");
		MIME_Types.put(".jar", "application/java-archive");
		MIME_Types.put(".java", "text/x-java");
		MIME_Types.put(".jdx", "chemical/x-jcamp-dx");
		MIME_Types.put(".jmz", "application/x-jmol");
		MIME_Types.put(".jng", "image/x-jng");
		MIME_Types.put(".jnlp", "application/x-java-jnlp-file");
		MIME_Types.put(".jpeg", "image/jpeg");
		MIME_Types.put(".jpe", "image/jpeg");
		MIME_Types.put(".jpg", "image/jpeg");
		MIME_Types.put(".js", "application/x-javascript");
		MIME_Types.put(".json", "application/json");
		MIME_Types.put(".kar", "audio/midi");
		MIME_Types.put(".key", "application/pgp-keys");
		MIME_Types.put(".kil", "application/x-killustrator");
		MIME_Types.put(".kin", "chemical/x-kinemage");
		MIME_Types.put(".kml", "application/vnd.google-earth.kml+xml");
		MIME_Types.put(".kmz", "application/vnd.google-earth.kmz");
		MIME_Types.put(".kpr", "application/x-kpresenter");
		MIME_Types.put(".kpt", "application/x-kpresenter");
		MIME_Types.put(".ksp", "application/x-kspread");
		MIME_Types.put(".kwd", "application/x-kword");
		MIME_Types.put(".kwt", "application/x-kword");
		MIME_Types.put(".lang", "text/x-lang");
		MIME_Types.put(".latex", "application/x-latex");
		MIME_Types.put(".lha", "application/x-lha");
		MIME_Types.put(".lhs", "text/x-literate-haskell");
		MIME_Types.put(".lnk", "text/plain");
		MIME_Types.put(".lsf", "video/x-la-asf");
		MIME_Types.put(".lsx", "video/x-la-asf");
		MIME_Types.put(".ltx", "text/x-tex");
		MIME_Types.put(".lyx", "application/x-lyx");
		MIME_Types.put(".lzh", "application/x-lzh");
		MIME_Types.put(".lzx", "application/x-lzx");
		MIME_Types.put(".$m3u", "audio/mpegurl");
		MIME_Types.put(".m3u", "audio/x-mpegurl");
		MIME_Types.put(".$m4a", "audio/mpeg");
		MIME_Types.put(".m4a", "audio/mp4");//"audio/mp4a-latm");//"audio/x-aac");//"audio/x-m4a");//"audio/m4a");//"video/mp4");//"audio/mpeg"); ...Sigh, nothing works right in google chrome, so mp4a-latm it is then. !!! Update: audio/mp4 works in chrome now! Yays. VLC Still uses "audio/x-m4a", so I hardcoded a fix for that.
		VLC_MIME_Types.put(".m4a", "audio/x-m4a");
		MIME_Types.put(".m4b", "video/mp4");
		MIME_Types.put(".m4p", "audio/aac");//"audio/mp4");
		MIME_Types.put(".m4v", "video/mp4");
		MIME_Types.put(".maker", "application/x-maker");
		MIME_Types.put(".man", "application/x-troff-man");
		MIME_Types.put(".manifest", "text/xml");//"application/xml");
		MIME_Types.put(".mcif", "chemical/x-mmcif");
		MIME_Types.put(".mcm", "chemical/x-macmolecule");
		MIME_Types.put(".mdb", "application/msaccess");
		MIME_Types.put(".me", "application/x-troff-me");
		MIME_Types.put(".mesh", "model/mesh");
		MIME_Types.put(".mid", "audio/midi");
		MIME_Types.put(".midi", "audio/midi");
		MIME_Types.put(".mif", "application/x-mif");
		MIME_Types.put(".mkv", "video/webm");
		VLC_MIME_Types.put(".mkv", "video/x-matroska");
		MIME_Types.put(".mm", "application/x-freemind");
		MIME_Types.put(".mmd", "chemical/x-macromodel-input");
		MIME_Types.put(".mmf", "application/vnd.smaf");
		MIME_Types.put(".mml", "text/mathml");
		MIME_Types.put(".mmod", "chemical/x-macromodel-input");
		MIME_Types.put(".mng", "video/x-mng");
		MIME_Types.put(".mobi", "application/x-mobipocket-ebook");
		MIME_Types.put(".moc", "text/x-moc");
		MIME_Types.put(".mol2", "chemical/x-mol2");
		MIME_Types.put(".mol", "chemical/x-mdl-molfile");
		MIME_Types.put(".moo", "chemical/x-mopac-out");
		MIME_Types.put(".mop", "chemical/x-mopac-input");
		MIME_Types.put(".mopcrt", "chemical/x-mopac-input");
		MIME_Types.put(".movie", "video/x-sgi-movie");
		MIME_Types.put(".mov", "video/quicktime");
		MIME_Types.put(".mp2", "audio/mpeg");
		MIME_Types.put(".mp3", "audio/mpeg");
		MIME_Types.put(".mp4", "video/mp4");//"video/mpeg");
		MIME_Types.put(".mpc", "chemical/x-mopac-input");
		MIME_Types.put(".mpega", "audio/mpeg");
		MIME_Types.put(".mpeg", "video/mpeg");
		MIME_Types.put(".mpe", "video/mpeg");
		MIME_Types.put(".mpga", "audio/mpeg");
		MIME_Types.put(".mpg", "video/mpeg");
		MIME_Types.put(".ms", "application/x-troff-ms");
		MIME_Types.put(".msh", "model/mesh");
		MIME_Types.put(".msi", "application/x-msi");
		MIME_Types.put(".mvb", "chemical/x-mopac-vib");
		MIME_Types.put(".mxu", "video/vnd.mpegurl");
		MIME_Types.put(".nb", "application/mathematica");
		MIME_Types.put(".nc", "application/x-netcdf");
		MIME_Types.put(".nwc", "application/x-nwc");
		MIME_Types.put(".o", "application/x-object");
		MIME_Types.put(".oda", "application/oda");
		MIME_Types.put(".odb", "application/vnd.oasis.opendocument.database");
		MIME_Types.put(".odc", "application/vnd.oasis.opendocument.chart");
		MIME_Types.put(".odf", "application/vnd.oasis.opendocument.formula");
		MIME_Types.put(".odg", "application/vnd.oasis.opendocument.graphics");
		MIME_Types.put(".odi", "application/vnd.oasis.opendocument.image");
		MIME_Types.put(".odm", "application/vnd.oasis.opendocument.text-master");
		MIME_Types.put(".odp", "application/vnd.oasis.opendocument.presentation");
		MIME_Types.put(".ods", "application/vnd.oasis.opendocument.spreadsheet");
		MIME_Types.put(".odt", "application/vnd.oasis.opendocument.text");
		MIME_Types.put(".ogg", "audio/ogg");//"application/ogg");
		MIME_Types.put(".oga", "audio/ogg");
		MIME_Types.put(".ogv", "video/ogg");
		MIME_Types.put(".ogx", "audio/ogg");//"application/ogg");
		MIME_Types.put(".old", "application/x-trash");
		MIME_Types.put(".otf", "font/otf");//"application/font-sfnt");
		MIME_Types.put(".otg", "application/vnd.oasis.opendocument.graphics-template");
		MIME_Types.put(".oth", "application/vnd.oasis.opendocument.text-web");
		MIME_Types.put(".otp", "application/vnd.oasis.opendocument.presentation-template");
		MIME_Types.put(".ots", "application/vnd.oasis.opendocument.spreadsheet-template");
		MIME_Types.put(".ott", "application/vnd.oasis.opendocument.text-template");
		MIME_Types.put(".oza", "application/x-oz-application");
		MIME_Types.put(".p7r", "application/x-pkcs7-certreqresp");
		MIME_Types.put(".pac", "application/x-ns-proxy-autoconfig");
		MIME_Types.put(".pas", "text/x-pascal");
		MIME_Types.put(".patch", "text/x-diff");
		MIME_Types.put(".pat", "image/x-coreldrawpattern");
		MIME_Types.put(".pbm", "image/x-portable-bitmap");
		MIME_Types.put(".pcap", "application/cap");
		MIME_Types.put(".pcf", "application/x-font");
		MIME_Types.put(".pcf.Z", "application/x-font");
		MIME_Types.put(".pcx", "image/pcx");
		MIME_Types.put(".pdb", "chemical/x-pdb");
		MIME_Types.put(".pdf", "application/pdf");
		MIME_Types.put(".pfa", "application/x-font");
		MIME_Types.put(".pfb", "application/x-font");
		MIME_Types.put(".pgm", "image/x-portable-graymap");
		MIME_Types.put(".pgn", "application/x-chess-pgn");
		MIME_Types.put(".pgp", "application/pgp-signature");
		MIME_Types.put(".php3", "application/x-httpd-php3");
		MIME_Types.put(".php3p", "application/x-httpd-php3-preprocessed");
		MIME_Types.put(".php4", "application/x-httpd-php4");
		MIME_Types.put(".php", "application/x-httpd-php");
		MIME_Types.put(".phps", "application/x-httpd-php-source");
		MIME_Types.put(".pht", "application/x-httpd-php");
		MIME_Types.put(".phtml", "application/x-httpd-php");
		MIME_Types.put(".pk", "application/x-tex-pk");
		MIME_Types.put(".pls", "audio/x-scpls");
		MIME_Types.put(".pl", "text/x-perl");
		MIME_Types.put(".pm", "text/x-perl");
		MIME_Types.put(".png", "image/png");
		MIME_Types.put(".pnm", "image/x-portable-anymap");
		MIME_Types.put(".pom", "text/xml");
		MIME_Types.put(".pot", "text/plain");
		MIME_Types.put(".ppm", "image/x-portable-pixmap");
		MIME_Types.put(".pps", "application/vnd.ms-powerpoint");
		MIME_Types.put(".ppt", "application/vnd.ms-powerpoint");
		MIME_Types.put(".pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
		MIME_Types.put(".prf", "application/pics-rules");
		MIME_Types.put(".prt", "chemical/x-ncbi-asn1-ascii");
		MIME_Types.put(".ps", "application/postscript");
		MIME_Types.put(".psd", "image/x-photoshop");
		MIME_Types.put(".p", "text/x-pascal");
		MIME_Types.put(".pyc", "application/x-python-code");
		MIME_Types.put(".pyo", "application/x-python-code");
		MIME_Types.put(".py", "text/x-python");
		MIME_Types.put(".qtl", "application/x-quicktimeplayer");
		MIME_Types.put(".qt", "video/quicktime");
		MIME_Types.put(".$ra", "audio/x-pn-realaudio");
		MIME_Types.put(".ra", "audio/x-realaudio");
		MIME_Types.put(".ram", "audio/x-pn-realaudio");
		MIME_Types.put(".rar", "application/x-rar-compressed");//"application/rar");
		MIME_Types.put(".ras", "image/x-cmu-raster");
		MIME_Types.put(".rd", "chemical/x-mdl-rdfile");
		MIME_Types.put(".rdf", "application/rdf+xml");
		MIME_Types.put(".rgb", "image/x-rgb");
		MIME_Types.put(".rhtml", "application/x-httpd-eruby");
		MIME_Types.put(".rm", "audio/x-pn-realaudio");
		MIME_Types.put(".roff", "application/x-troff");
		MIME_Types.put(".ros", "chemical/x-rosdal");
		MIME_Types.put(".rpm", "application/x-redhat-package-manager");
		MIME_Types.put(".rss", "application/rss+xml");
		MIME_Types.put(".rtf", "application/rtf");
		MIME_Types.put(".rtx", "text/richtext");
		MIME_Types.put(".rxn", "chemical/x-mdl-rxnfile");
		MIME_Types.put(".sct", "text/scriptlet");
		MIME_Types.put(".sd2", "audio/x-sd2");
		MIME_Types.put(".sda", "application/vnd.stardivision.draw");
		MIME_Types.put(".sdc", "application/vnd.stardivision.calc");
		MIME_Types.put(".sd", "chemical/x-mdl-sdfile");
		MIME_Types.put(".sdd", "application/vnd.stardivision.impress");
		MIME_Types.put(".$sdf", "application/vnd.stardivision.math");
		MIME_Types.put(".sdf", "chemical/x-mdl-sdfile");
		MIME_Types.put(".sds", "application/vnd.stardivision.chart");
		MIME_Types.put(".sdw", "application/vnd.stardivision.writer");
		MIME_Types.put(".ser", "application/java-serialized-object");
		MIME_Types.put(".sfnt", "font/sfnt");//"application/font-sfnt");
		MIME_Types.put(".sgf", "application/x-go-sgf");
		MIME_Types.put(".sgl", "application/vnd.stardivision.writer-global");
		MIME_Types.put(".$sh", "application/x-sh");
		MIME_Types.put(".shar", "application/x-shar");
		MIME_Types.put(".sh", "text/x-sh");
		MIME_Types.put(".shtml", "text/html");
		MIME_Types.put(".sid", "audio/prs.sid");
		MIME_Types.put(".sik", "application/x-trash");
		MIME_Types.put(".silo", "model/mesh");
		MIME_Types.put(".sis", "application/vnd.symbian.install");
		MIME_Types.put(".sisx", "x-epoc/x-sisx-app");
		MIME_Types.put(".sit", "application/x-stuffit");
		MIME_Types.put(".sitx", "application/x-stuffit");
		MIME_Types.put(".skd", "application/x-koan");
		MIME_Types.put(".skm", "application/x-koan");
		MIME_Types.put(".skp", "application/x-koan");
		MIME_Types.put(".skt", "application/x-koan");
		MIME_Types.put(".smi", "application/smil");
		MIME_Types.put(".smil", "application/smil");
		MIME_Types.put(".snd", "audio/basic");
		MIME_Types.put(".spc", "chemical/x-galactic-spc");
		MIME_Types.put(".$spl", "application/futuresplash");
		MIME_Types.put(".spl", "application/x-futuresplash");
		MIME_Types.put(".spx", "audio/ogg");
		MIME_Types.put(".src", "application/x-wais-source");
		MIME_Types.put(".stc", "application/vnd.sun.xml.calc.template");
		MIME_Types.put(".std", "application/vnd.sun.xml.draw.template");
		MIME_Types.put(".sti", "application/vnd.sun.xml.impress.template");
		MIME_Types.put(".stl", "application/vnd.ms-pki.stl");
		MIME_Types.put(".stw", "application/vnd.sun.xml.writer.template");
		MIME_Types.put(".sty", "text/x-tex");
		MIME_Types.put(".sv4cpio", "application/x-sv4cpio");
		MIME_Types.put(".sv4crc", "application/x-sv4crc");
		MIME_Types.put(".svg", "image/svg+xml");
		MIME_Types.put(".svgz", "image/svg+xml");
		MIME_Types.put(".sw", "chemical/x-swissprot");
		MIME_Types.put(".swf", "application/x-shockwave-flash");
		MIME_Types.put(".swfl", "application/x-shockwave-flash");
		MIME_Types.put(".sxc", "application/vnd.sun.xml.calc");
		MIME_Types.put(".sxd", "application/vnd.sun.xml.draw");
		MIME_Types.put(".sxg", "application/vnd.sun.xml.writer.global");
		MIME_Types.put(".sxi", "application/vnd.sun.xml.impress");
		MIME_Types.put(".sxm", "application/vnd.sun.xml.math");
		MIME_Types.put(".sxw", "application/vnd.sun.xml.writer");
		MIME_Types.put(".t", "application/x-troff");
		MIME_Types.put(".tar", "application/x-tar");
		MIME_Types.put(".taz", "application/x-gtar");
		MIME_Types.put(".$tcl", "application/x-tcl");
		MIME_Types.put(".tcl", "text/x-tcl");
		MIME_Types.put(".texi", "application/x-texinfo");
		MIME_Types.put(".texinfo", "application/x-texinfo");
		MIME_Types.put(".tex", "text/x-tex");
		MIME_Types.put(".text", "text/plain");
		MIME_Types.put(".tgf", "chemical/x-mdl-tgf");
		MIME_Types.put(".tgz", "application/x-gtar");
		MIME_Types.put(".themepack", "application/zip");
		MIME_Types.put(".tiff", "image/tiff");
		MIME_Types.put(".tif", "image/tiff");
		MIME_Types.put(".tk", "text/x-tcl");
		MIME_Types.put(".tm", "text/texmacs");
		MIME_Types.put(".torrent", "application/x-bittorrent");
		MIME_Types.put(".tr", "application/x-troff");
		MIME_Types.put(".tsp", "application/dsptype");
		MIME_Types.put(".ts", "text/texmacs");
		MIME_Types.put(".tsv", "text/tab-separated-values");
		MIME_Types.put(".ttf", "font/ttf");//"application/font-sfnt");
		MIME_Types.put(".txt", "text/plain");
		MIME_Types.put(".udeb", "application/x-debian-package");
		MIME_Types.put(".uls", "text/iuls");
		MIME_Types.put(".url", "text/plain");
		MIME_Types.put(".ustar", "application/x-ustar");
		MIME_Types.put(".val", "chemical/x-ncbi-asn1-binary");
		MIME_Types.put(".vbe", "text/x-vbe");
		MIME_Types.put(".vbs", "text/x-vbs");
		MIME_Types.put(".vcd", "application/x-cdlink");
		MIME_Types.put(".vcf", "text/x-vcard");
		MIME_Types.put(".vcs", "text/x-vcalendar");
		MIME_Types.put(".vmd", "chemical/x-vmd");
		MIME_Types.put(".vms", "chemical/x-vamas-iso14976");
		MIME_Types.put(".vp8", "video/webm");//"video/mpeg");
		MIME_Types.put(".vp9", "video/webm");
		MIME_Types.put(".$vrml", "model/vrml");
		MIME_Types.put(".vrml", "x-world/x-vrml");
		MIME_Types.put(".vrm", "x-world/x-vrml");
		MIME_Types.put(".vsd", "application/vnd.visio");
		MIME_Types.put(".wad", "application/x-doom");
		MIME_Types.put(".wav", "audio/x-wav");
		MIME_Types.put(".wax", "audio/x-ms-wax");
		MIME_Types.put(".wbmp", "image/vnd.wap.wbmp");
		MIME_Types.put(".wbxml", "application/vnd.wap.wbxml");
		MIME_Types.put(".webm", "video/webm");
		MIME_Types.put(".webp", "image/webp");
		MIME_Types.put(".wk", "application/x-123");
		MIME_Types.put(".wma", "audio/x-ms-wma");
		MIME_Types.put(".wmd", "application/x-ms-wmd");
		MIME_Types.put(".wmlc", "application/vnd.wap.wmlc");
		MIME_Types.put(".wmlsc", "application/vnd.wap.wmlscriptc");
		MIME_Types.put(".wmls", "text/vnd.wap.wmlscript");
		MIME_Types.put(".wml", "text/vnd.wap.wml");
		MIME_Types.put(".wm", "video/x-ms-wm");
		MIME_Types.put(".wmv", "video/x-ms-wmv");
		MIME_Types.put(".wmx", "video/x-ms-wmx");
		MIME_Types.put(".wmz", "application/x-ms-wmz");
		MIME_Types.put(".woff", "font/woff");//"application/font-woff");
		MIME_Types.put(".woff2", "font/woff2");//"application/font-woff2");
		MIME_Types.put(".wp5", "application/wordperfect5.1");
		MIME_Types.put(".wpd", "application/wordperfect");
		MIME_Types.put(".$wrl", "model/vrml");
		MIME_Types.put(".wrl", "x-world/x-vrml");
		MIME_Types.put(".wsc", "text/scriptlet");
		MIME_Types.put(".wvx", "video/x-ms-wvx");
		MIME_Types.put(".wz", "application/x-wingz");
		MIME_Types.put(".xbm", "image/x-xbitmap");
		MIME_Types.put(".xcf", "application/x-xcf");
		MIME_Types.put(".xht", "application/xhtml+xml");
		MIME_Types.put(".xhtml", "application/xhtml+xml");
		MIME_Types.put(".xlb", "application/vnd.ms-excel");
		MIME_Types.put(".xls", "application/vnd.ms-excel");
		MIME_Types.put(".xlt", "application/vnd.ms-excel");
		MIME_Types.put(".xml", "application/xml");
		MIME_Types.put(".xpi", "application/x-xpinstall");
		MIME_Types.put(".xpm", "image/x-xpixmap");
		MIME_Types.put(".xsl", "application/xml");
		MIME_Types.put(".xspf", "application/xspf+xml");//"text/xml");//"text/plain");
		MIME_Types.put(".xtel", "chemical/x-xtel");
		MIME_Types.put(".xul", "application/vnd.mozilla.xul+xml");
		MIME_Types.put(".xwd", "image/x-xwindowdump");
		MIME_Types.put(".xyz", "chemical/x-xyz");
		MIME_Types.put(".yml", "text/x-yaml");
		MIME_Types.put(".zip", "application/zip");
		MIME_Types.put(".zmt", "chemical/x-mopac-input");
		MIME_Types.put(".", DEFAULT_MIME_TYPE);
		MIME_Types.put(".classpath", "text/xml");
		MIME_Types.put(".project", "text/xml");
		MIME_Types.put(".properties", "text/plain");
		MIME_Types.put(".mf", "text/plain");
		
		//===
		
		MIME_Types.put(".3dm", "x-world/x-3dmf");
		MIME_Types.put(".3dmf", "x-world/x-3dmf");
		MIME_Types.put(".a", DEFAULT_MIME_TYPE);
		MIME_Types.put(".aab", "application/x-authorware-bin");
		MIME_Types.put(".aam", "application/x-authorware-map");
		MIME_Types.put(".aas", "application/x-authorware-seg");
		MIME_Types.put(".abc", "text/vnd.abc");
		MIME_Types.put(".acgi", "text/html");
		MIME_Types.put(".afl", "video/animaflex");
		MIME_Types.put(".aim", "application/x-aim");
		MIME_Types.put(".aip", "text/x-audiosoft-intra");
		MIME_Types.put(".ani", "application/x-navi-animation");
		MIME_Types.put(".aos", "application/x-nokia-9000-communicator-add-on-software");
		MIME_Types.put(".aps", "application/mime");
		MIME_Types.put(".arc", DEFAULT_MIME_TYPE);
		MIME_Types.put(".arj", "application/arj");
		MIME_Types.put(".arj", DEFAULT_MIME_TYPE);
		MIME_Types.put(".asm", "text/x-asm");
		MIME_Types.put(".asp", "text/asp");
		MIME_Types.put(".avs", "video/avs-video");
		MIME_Types.put(".bm", "image/bmp");
		MIME_Types.put(".boz", "application/x-bzip2");
		MIME_Types.put(".bsh", "application/x-bsh");
		MIME_Types.put(".bz", "application/x-bzip");
		MIME_Types.put(".bz2", "application/x-bzip2");
		MIME_Types.put(".c++", "text/plain");
		MIME_Types.put(".ccad", "application/clariscad");
		MIME_Types.put(".cco", "application/x-cocoa");
		MIME_Types.put(".cha", "application/x-chat");
		MIME_Types.put(".chat", "application/x-chat");
		MIME_Types.put(".conf", "text/plain");
		MIME_Types.put(".config", "text/plain");
		MIME_Types.put(".deepv", "application/x-deepv");
		MIME_Types.put(".def", "text/plain");
		MIME_Types.put(".der", "application/x-x509-ca-cert");
		MIME_Types.put(".dp", "application/commonground");
		MIME_Types.put(".drw", "application/drafting");
		MIME_Types.put(".dump", DEFAULT_MIME_TYPE);
		MIME_Types.put(".dwf", "drawing/x-dwf (old)");
		MIME_Types.put(".dwf", "model/vnd.dwf");
		MIME_Types.put(".dwg", "application/acad");
		MIME_Types.put(".dwg", "image/vnd.dwg");
		MIME_Types.put(".dwg", "image/x-dwg");
		MIME_Types.put(".dxf", "application/dxf");
		MIME_Types.put(".dxf", "image/vnd.dwg");
		MIME_Types.put(".dxf", "image/x-dwg");
		MIME_Types.put(".el", "text/x-script.elisp");
		MIME_Types.put(".elc", "application/x-bytecode.elisp");
		MIME_Types.put(".elc", "application/x-elc");
		MIME_Types.put(".env", "application/x-envoy");
		MIME_Types.put(".es", "application/x-esrehber");
		MIME_Types.put(".evy", "application/envoy");
		MIME_Types.put(".evy", "application/x-envoy");
		MIME_Types.put(".f", "text/plain");
		MIME_Types.put(".f", "text/x-fortran");
		MIME_Types.put(".f77", "text/x-fortran");
		MIME_Types.put(".f90", "text/plain");
		MIME_Types.put(".f90", "text/x-fortran");
		MIME_Types.put(".fdf", "application/vnd.fdf");
		MIME_Types.put(".fif", "application/fractals");
		MIME_Types.put(".fif", "image/fif");
		MIME_Types.put(".flo", "image/florian");
		MIME_Types.put(".flx", "text/vnd.fmi.flexstor");
		MIME_Types.put(".fmf", "video/x-atomic3d-feature");
		MIME_Types.put(".for", "text/plain");
		MIME_Types.put(".for", "text/x-fortran");
		MIME_Types.put(".fpx", "image/vnd.fpx");
		MIME_Types.put(".fpx", "image/vnd.net-fpx");
		MIME_Types.put(".frl", "application/freeloader");
		MIME_Types.put(".funk", "audio/make");
		MIME_Types.put(".g", "text/plain");
		MIME_Types.put(".g3", "image/g3fax");
		MIME_Types.put(".gsd", "audio/x-gsm");
		MIME_Types.put(".gsp", "application/x-gsp");
		MIME_Types.put(".gss", "application/x-gss");
		MIME_Types.put(".gz", "application/x-compressed");
		MIME_Types.put(".gz", "application/x-gzip");
		MIME_Types.put(".gzip", "application/x-gzip");
		//MIME_Types.put(".gzip", "multipart/x-gzip");
		MIME_Types.put(".h", "text/plain");
		//MIME_Types.put(".h", "text/x-h");
		MIME_Types.put(".help", "application/x-helpfile");
		MIME_Types.put(".hgl", "application/vnd.hp-hpgl");
		MIME_Types.put(".hlb", "text/x-script");
		//MIME_Types.put(".hlp", "application/hlp");
		MIME_Types.put(".hlp", "application/x-helpfile");
		//MIME_Types.put(".hlp", "application/x-winhelp");
		MIME_Types.put(".hpg", "application/vnd.hp-hpgl");
		MIME_Types.put(".hpgl", "application/vnd.hp-hpgl");
		MIME_Types.put(".htmls", "text/html");
		MIME_Types.put(".htt", "text/webviewhtml");
		MIME_Types.put(".htx", "text/html");
		MIME_Types.put(".idc", "text/plain");
		MIME_Types.put(".iefs", "image/ief");
		MIME_Types.put(".ima", "application/x-ima");
		MIME_Types.put(".imap", "application/x-httpd-imap");
		MIME_Types.put(".inf", "application/inf");
		MIME_Types.put(".ip", "application/x-ip2");
		MIME_Types.put(".isu", "video/x-isvideo");
		MIME_Types.put(".it", "audio/it");
		MIME_Types.put(".iv", "application/x-inventor");
		MIME_Types.put(".ivr", "i-world/i-vrml");
		MIME_Types.put(".ivy", "application/x-livescreen");
		MIME_Types.put(".jam", "audio/x-jam");
		//MIME_Types.put(".jav", "text/plain");
		MIME_Types.put(".jav", "text/x-java-source");
		MIME_Types.put(".jcm", "application/x-java-commerce");
		MIME_Types.put(".jfif", "image/jpeg");
		MIME_Types.put(".jfif", "image/pjpeg");
		MIME_Types.put(".jfif-tbnl", "image/jpeg");
		MIME_Types.put(".jps", "image/x-jps");
		MIME_Types.put(".jut", "image/jutvision");
		MIME_Types.put(".ksh", "application/x-ksh");
		MIME_Types.put(".ksh", "text/x-script.ksh");
		MIME_Types.put(".la", "audio/nspaudio");
		MIME_Types.put(".la", "audio/x-nspaudio");
		MIME_Types.put(".lam", "audio/x-liveaudio");
		MIME_Types.put(".lhx", DEFAULT_MIME_TYPE);
		MIME_Types.put(".list", "text/plain");
		MIME_Types.put(".lma", "audio/nspaudio");
		MIME_Types.put(".lma", "audio/x-nspaudio");
		MIME_Types.put(".log", "text/plain");
		MIME_Types.put(".lsp", "application/x-lisp");
		MIME_Types.put(".lsp", "text/x-script.lisp");
		MIME_Types.put(".lst", "text/plain");
		MIME_Types.put(".m", "text/plain");
		MIME_Types.put(".m", "text/x-m");
		MIME_Types.put(".m1v", "video/mpeg");
		MIME_Types.put(".m2a", "audio/mpeg");
		MIME_Types.put(".m2v", "video/mpeg");
		MIME_Types.put(".map", "application/x-navimap");
		MIME_Types.put(".mar", "text/plain");
		MIME_Types.put(".mbd", "application/mbedlet");
		MIME_Types.put(".mc$", "application/x-magic-cap-package-1.0");
		MIME_Types.put(".mcd", "application/mcad");
		MIME_Types.put(".mcd", "application/x-mathcad");
		MIME_Types.put(".mcf", "image/vasa");
		MIME_Types.put(".mcf", "text/mcf");
		MIME_Types.put(".mcp", "application/netmc");
		MIME_Types.put(".md", "text/x-markdown");
		MIME_Types.put(".mht", "message/rfc822");
		MIME_Types.put(".mhtml", "message/rfc822");
		MIME_Types.put(".mime", "message/rfc822");
		MIME_Types.put(".mime", "www/mime");
		MIME_Types.put(".mjf", "audio/x-vnd.audioexplosion.mjuicemediafile");
		MIME_Types.put(".mjpg", "video/x-motion-jpeg");
		MIME_Types.put(".mme", "application/base64");
		MIME_Types.put(".mod", "audio/mod");
		MIME_Types.put(".mod", "audio/x-mod");
		MIME_Types.put(".moov", "video/quicktime");
		MIME_Types.put(".mpa", "audio/mpeg");
		MIME_Types.put(".mpa", "video/mpeg");
		MIME_Types.put(".mpp", "application/vnd.ms-project");
		MIME_Types.put(".mpt", "application/x-project");
		MIME_Types.put(".mpv", "application/x-project");
		MIME_Types.put(".mpx", "application/x-project");
		MIME_Types.put(".mrc", "application/marc");
		MIME_Types.put(".mv", "video/x-sgi-movie");
		MIME_Types.put(".my", "audio/make");
		MIME_Types.put(".mzz", "application/x-vnd.audioexplosion.mzz");
		MIME_Types.put(".nap", "image/naplps");
		MIME_Types.put(".naplps", "image/naplps");
		MIME_Types.put(".ncm", "application/vnd.nokia.configuration-message");
		MIME_Types.put(".nif", "image/x-niff");
		MIME_Types.put(".niff", "image/x-niff");
		MIME_Types.put(".nix", "application/x-mix-transfer");
		MIME_Types.put(".nsc", "application/x-conference");
		MIME_Types.put(".nvd", "application/x-navidoc");
		MIME_Types.put(".omc", "application/x-omc");
		MIME_Types.put(".omcd", "application/x-omcdatamaker");
		MIME_Types.put(".omcr", "application/x-omcregerator");
		MIME_Types.put(".p10", "application/pkcs10");
		//MIME_Types.put(".p10", "application/x-pkcs10");
		MIME_Types.put(".p12", "application/pkcs-12");
		//MIME_Types.put(".p12", "application/x-pkcs12");
		MIME_Types.put(".p7a", "application/x-pkcs7-signature");
		MIME_Types.put(".p7c", "application/pkcs7-mime");
		//MIME_Types.put(".p7c", "application/x-pkcs7-mime");
		MIME_Types.put(".p7m", "application/pkcs7-mime");
		//MIME_Types.put(".p7m", "application/x-pkcs7-mime");
		MIME_Types.put(".p7s", "application/pkcs7-signature");
		MIME_Types.put(".part", "application/pro_eng");
		MIME_Types.put(".pcl", "application/vnd.hp-pcl");
		//MIME_Types.put(".pcl", "application/x-pcl");
		MIME_Types.put(".pct", "image/x-pict");
		MIME_Types.put(".pfunk", "audio/make");
		//MIME_Types.put(".pfunk", "audio/make.my.funk");
		MIME_Types.put(".pic", "image/pict");
		MIME_Types.put(".pict", "image/pict");
		MIME_Types.put(".pkg", "application/x-newton-compatible-pkg");
		MIME_Types.put(".pko", "application/vnd.ms-pki.pko");
		MIME_Types.put(".plx", "application/x-pixclscript");
		MIME_Types.put(".pm4", "application/x-pagemaker");
		MIME_Types.put(".pm5", "application/x-pagemaker");
		MIME_Types.put(".pov", "model/x-pov");
		MIME_Types.put(".ppa", "application/vnd.ms-powerpoint");
		MIME_Types.put(".ppz", "application/mspowerpoint");
		MIME_Types.put(".pre", "application/x-freelance");
		MIME_Types.put(".pvu", "paleovu/x-pv");
		MIME_Types.put(".pwz", "application/vnd.ms-powerpoint");
		MIME_Types.put(".qcp", "audio/vnd.qcelp");
		MIME_Types.put(".qd3", "x-world/x-3dmf");
		MIME_Types.put(".qd3d", "x-world/x-3dmf");
		MIME_Types.put(".qif", "image/x-quicktime");
		MIME_Types.put(".qtc", "video/x-qtc");
		MIME_Types.put(".qti", "image/x-quicktime");
		MIME_Types.put(".qtif", "image/x-quicktime");
		MIME_Types.put(".rast", "image/cmu-raster");
		MIME_Types.put(".readme", "text/plain");
		MIME_Types.put(".reg", "application/registry-editor");//"text/plain");
		MIME_Types.put(".rexx", "text/x-script.rexx");
		MIME_Types.put(".rf", "image/vnd.rn-realflash");
		MIME_Types.put(".rmi", "audio/mid");
		MIME_Types.put(".rmm", "audio/x-pn-realaudio");
		MIME_Types.put(".rmp", "audio/x-pn-realaudio");
		//MIME_Types.put(".rmp", "audio/x-pn-realaudio-plugin");
		MIME_Types.put(".rng", "application/ringing-tones");
		//MIME_Types.put(".rng", "application/vnd.nokia.ringing-tone");
		MIME_Types.put(".rnx", "application/vnd.rn-realplayer");
		MIME_Types.put(".rp", "image/vnd.rn-realpix");
		MIME_Types.put(".rt", "text/richtext");
		//MIME_Types.put(".rt", "text/vnd.rn-realtext");
		MIME_Types.put(".rv", "video/vnd.rn-realvideo");
		MIME_Types.put(".s", "text/x-asm");
		MIME_Types.put(".s3m", "audio/s3m");
		//MIME_Types.put(".saveme", DEFAULT_MIME_TYPE);
		MIME_Types.put(".sbk", "application/x-tbook");
		//MIME_Types.put(".scm", "application/x-lotusscreencam");
		//MIME_Types.put(".scm", "text/x-script.guile");
		MIME_Types.put(".scm", "text/x-script.scheme");
		//MIME_Types.put(".scm", "video/x-scm");
		MIME_Types.put(".sdml", "text/plain");
		MIME_Types.put(".sdp", "application/sdp");
		//MIME_Types.put(".sdp", "application/x-sdp");
		MIME_Types.put(".sdr", "application/sounder");
		MIME_Types.put(".sea", "application/sea");
		//MIME_Types.put(".sea", "application/x-sea");
		MIME_Types.put(".set", "application/set");
		MIME_Types.put(".sgm", "text/sgml");
		//MIME_Types.put(".sgm", "text/x-sgml");
		MIME_Types.put(".sgml", "text/sgml");
		//MIME_Types.put(".sgml", "text/x-sgml");
		MIME_Types.put(".sl", "application/x-seelogo");
		MIME_Types.put(".sol", "application/solids");
		MIME_Types.put(".spr", "application/x-sprite");
		MIME_Types.put(".sprite", "application/x-sprite");
		MIME_Types.put(".ssi", "text/x-server-parsed-html");
		MIME_Types.put(".ssm", "application/streamingmedia");
		MIME_Types.put(".sst", "application/vnd.ms-pki.certstore");
		MIME_Types.put(".step", "application/step");
		MIME_Types.put(".stp", "application/step");
		MIME_Types.put(".svf", "image/vnd.dwg");
		//MIME_Types.put(".svf", "image/x-dwg");
		MIME_Types.put(".svr", "application/x-world");
		//MIME_Types.put(".svr", "x-world/x-svr");
		MIME_Types.put(".talk", "text/x-speech");
		MIME_Types.put(".tbk", "application/toolbook");
		//MIME_Types.put(".tbk", "application/x-tbook");
		MIME_Types.put(".tcsh", "text/x-script.tcsh");
		MIME_Types.put(".tsi", "audio/tsp-audio");
		MIME_Types.put(".turbot", "image/florian");
		MIME_Types.put(".uil", "text/x-uil");
		MIME_Types.put(".uni", "text/uri-list");
		MIME_Types.put(".unis", "text/uri-list");
		MIME_Types.put(".unv", "application/i-deas");
		MIME_Types.put(".uri", "text/uri-list");
		MIME_Types.put(".uris", "text/uri-list");
		//MIME_Types.put(".uu", DEFAULT_MIME_TYPE);
		MIME_Types.put(".uu", "text/x-uuencode");
		MIME_Types.put(".uue", "text/x-uuencode");
		MIME_Types.put(".vda", "application/vda");
		MIME_Types.put(".vdo", "video/vdo");
		MIME_Types.put(".vew", "application/groupwise");
		//MIME_Types.put(".viv", "video/vivo");
		MIME_Types.put(".viv", "video/vnd.vivo");
		//MIME_Types.put(".vivo", "video/vivo");
		MIME_Types.put(".vivo", "video/vnd.vivo");
		MIME_Types.put(".vmf", "application/vocaltec-media-file");
		MIME_Types.put(".voc", "audio/voc");
		//MIME_Types.put(".voc", "audio/x-voc");
		MIME_Types.put(".vos", "video/vosaic");
		MIME_Types.put(".vox", "audio/voxware");
		MIME_Types.put(".vqe", "audio/x-twinvq-plugin");
		MIME_Types.put(".vqf", "audio/x-twinvq");
		MIME_Types.put(".vql", "audio/x-twinvq-plugin");
		MIME_Types.put(".vrt", "x-world/x-vrt");
		MIME_Types.put(".vst", "application/x-visio");
		MIME_Types.put(".vsw", "application/x-visio");
		MIME_Types.put(".w60", "application/wordperfect6.0");
		MIME_Types.put(".w61", "application/wordperfect6.1");
		MIME_Types.put(".w6w", "application/msword");
		MIME_Types.put(".wb1", "application/x-qpro");
		MIME_Types.put(".web", "application/vnd.xara");
		MIME_Types.put(".wiz", "application/msword");
		MIME_Types.put(".wk1", "application/x-123");
		MIME_Types.put(".wmf", "windows/metafile");
		MIME_Types.put(".word", "application/msword");
		MIME_Types.put(".wp", "application/wordperfect");
		MIME_Types.put(".wp6", "application/wordperfect");
		MIME_Types.put(".wq1", "application/x-lotus");
		MIME_Types.put(".wri", "application/mswrite");
		//MIME_Types.put(".wri", "application/x-wri");
		MIME_Types.put(".wrz", "model/vrml");
		//MIME_Types.put(".wrz", "x-world/x-vrml");
		MIME_Types.put(".wsrc", "application/x-wais-source");
		MIME_Types.put(".wtk", "application/x-wintalk");
		MIME_Types.put(".xdr", "video/x-amt-demorun");
		MIME_Types.put(".xgz", "xgl/drawing");
		MIME_Types.put(".xif", "image/vnd.xiff");
		MIME_Types.put(".xl", "application/excel");
		MIME_Types.put(".xla", "application/excel");
		//MIME_Types.put(".xla", "application/x-excel");
		//MIME_Types.put(".xla", "application/x-msexcel");
		//MIME_Types.put(".xlc", "application/excel");
		MIME_Types.put(".xlc", "application/vnd.ms-excel");
		//MIME_Types.put(".xlc", "application/x-excel");
		MIME_Types.put(".xld", "application/excel");
		//MIME_Types.put(".xld", "application/x-excel");
		MIME_Types.put(".xlk", "application/excel");
		//MIME_Types.put(".xlk", "application/x-excel");
		//MIME_Types.put(".xll", "application/excel");
		MIME_Types.put(".xll", "application/vnd.ms-excel");
		//MIME_Types.put(".xll", "application/x-excel");
		//MIME_Types.put(".xlm", "application/excel");
		MIME_Types.put(".xlm", "application/vnd.ms-excel");
		//MIME_Types.put(".xlm", "application/x-excel");
		MIME_Types.put(".xlv", "application/excel");
		//MIME_Types.put(".xlv", "application/x-excel");
		//MIME_Types.put(".xlw", "application/excel");
		MIME_Types.put(".xlw", "application/vnd.ms-excel");
		//MIME_Types.put(".xlw", "application/x-excel");
		//MIME_Types.put(".xlw", "application/x-msexcel");
		MIME_Types.put(".xm", "audio/xm");
		MIME_Types.put(".xmz", "xgl/movie");
		MIME_Types.put(".xpix", "application/x-vnd.ls-xpix");
		MIME_Types.put(".x-png", "image/png");
		MIME_Types.put(".xsr", "video/x-amt-showrun");
		//MIME_Types.put(".z", "application/x-compress");
		MIME_Types.put(".z", "application/x-compressed");
		MIME_Types.put(".zoo", DEFAULT_MIME_TYPE);
		MIME_Types.put(".zsh", "text/x-script.zsh");
		
		MIME_Types.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		MIME_Types.put(".docm", "application/vnd.ms-word.document.macroEnabled.12");
		MIME_Types.put(".dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
		MIME_Types.put(".dotm", "application/vnd.ms-word.template.macroEnabled.12");
		MIME_Types.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		MIME_Types.put(".xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
		MIME_Types.put(".xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
		MIME_Types.put(".xltm", "application/vnd.ms-excel.template.macroEnabled.12");
		MIME_Types.put(".xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
		MIME_Types.put(".xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
		MIME_Types.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		MIME_Types.put(".pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
		MIME_Types.put(".ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
		MIME_Types.put(".ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
		MIME_Types.put(".potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
		MIME_Types.put(".potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
		MIME_Types.put(".ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
		MIME_Types.put(".sldx", "application/vnd.openxmlformats-officedocument.presentationml.slide");
		MIME_Types.put(".sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12");
		MIME_Types.put(".one", "application/msonenote");
		MIME_Types.put(".onetoc2", "application/msonenote");
		MIME_Types.put(".onetmp", "application/msonenote");
		MIME_Types.put(".onepkg", "application/msonenote");
		MIME_Types.put(".thmx", "application/vnd.ms-officetheme");
		
		MIME_Types.put(".webarchive", "application/x-webarchive");
		MIME_Types.put(".rdp", "application/x-rdp");// Remote desktop connection
		
		try {
			File folder = new File(System.getProperty("user.dir"));
			folder.mkdirs();
			File ymlFile = new File(folder, "mimeTypes.yml");
			if(!ymlFile.exists()) {
				ymlFile.createNewFile();
			}
			File txtFile = new File(folder, "mimeTypes.txt");
			if(!txtFile.exists()) {
				txtFile.createNewFile();
			}
			
			try(PrintWriter txtPr = new PrintWriter(new OutputStreamWriter(new FileOutputStream(txtFile), StandardCharsets.UTF_8), true)) {
				try(PrintWriter ymlPr = new PrintWriter(new OutputStreamWriter(new FileOutputStream(ymlFile), StandardCharsets.UTF_8), true)) {
					ymlPr.println("# This file is for reference only and is updated each time the server is run.\r\n# Changes made here will be lost when the server restarts.\r\n# These mime types are used by the server when serving files,\r\n# and are the global default across all domains.\r\n# If a domain overrides a mime type, it will be saved in that domain's save file.\r\n#");
					txtPr.println("This file is for reference only and is updated each time the server is run.\r\nChanges made here will be lost when the server restarts.\r\nThese mime types are used by the server when serving files,\r\nand are the global default across all domains.\r\nIf a domain overrides a mime type, it will be saved in that domain's save file.\r\n");
					for(Entry<String, String> entry : new TreeMap<>(MIME_Types).entrySet()) {
						ymlPr.println("\"" + entry.getKey() + "\": |-\r\n  " + entry.getValue());
						txtPr.println(entry.getKey() + ": " + entry.getValue());
					}
					
					ymlPr.flush();
				}
				txtPr.flush();
			}
		} catch(Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	/** @param list The list of strings to search through
	 * @param str The string to search for
	 * @return True if the list contained any instance of the given string,
	 *         ignoring case */
	private static final boolean containsIgnoreCase(Collection<String> list, String str) {
		if(str != null && list != null && !list.isEmpty()) {
			for(String s : new ArrayList<>(list)) {
				if(str.equalsIgnoreCase(s)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static final String getFileName(String filePath) {
		filePath = filePath.replace('\\', '/');
		//filePath = filePath.endsWith("/") ? filePath.substring(0, filePath.length() - 1) : filePath;
		return filePath.contains("/") ? filePath.substring(filePath.lastIndexOf('/') + 1) : filePath;
	}
	
	private static final String getFileBaseName(String filePath) {
		filePath = getFileName(filePath);
		return filePath.contains(".") ? filePath.substring(0, filePath.lastIndexOf('.')) : filePath;
	}
	
	/** Returns the file's extension, if one is present, or an empty string
	 * otherwise.<br>
	 * Examples:
	 * <ul>
	 * <li><tt>&quot;fileName.txt&quot;</tt> --&gt;
	 * <tt>&quot;txt&quot;</tt></li>
	 * <li><tt>&quot;/some/File/Path.txt&quot;</tt> --&gt;
	 * <tt>&quot;txt&quot;</tt></li>
	 * <li><tt>&quot;/some/File/Path&quot;</tt> --&gt;
	 * <tt>&quot;&quot;</tt></li>
	 * <li><tt>&quot;/some/File/Path/&quot;</tt> --&gt;
	 * <tt>&quot;&quot;</tt></li>
	 * <li><tt>&quot;/some/File/Path.tar.gz&quot;</tt> --&gt;
	 * <tt>&quot;gz&quot;</tt></li>
	 * <li><tt>&quot;/some/File/Path.tar.&quot;</tt> --&gt;
	 * <tt>&quot;&quot;</tt></li>
	 * </ul>
	 * 
	 * @param filePath The path to (or name of) the file
	 * @return The file's extension, if present */
	private static final String getFileExtension(String filePath) {
		filePath = getFileName(filePath);
		if(filePath.contains(".")) {
			return filePath.substring(filePath.lastIndexOf('.') + 1);
		}
		return "";
	}
	
	/** @param ext The file extension
	 * @return The resulting mime type, or the {@link #DEFAULT_MIME_TYPE} if the
	 *         given extension did not have an associated MIME type */
	public static final String getMimeTypeForExtension(String ext) {
		if(ext == null) {
			return null;
		}
		if(!ext.startsWith(".")) {
			ext = "." + ext;
		}
		String mimeType = null;
		for(Entry<String, String> entry : MIME_Types.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(ext)) {
				mimeType = entry.getValue();
				break;
			}
		}
		return mimeType != null ? mimeType : DEFAULT_MIME_TYPE;
	}
	
	public static final List<String> getExtensionsForMimeType(String mimeType, String... fallbackExtensions) {
		List<String> list = new ArrayList<>();
		for(Entry<String, String> entry : MIME_Types.entrySet()) {
			if(entry.getValue().equalsIgnoreCase(mimeType)) {
				String key = entry.getKey();
				if(!containsIgnoreCase(list, key)) {
					list.add(key);
				}
			}
		}
		return list.isEmpty() ? (fallbackExtensions == null || fallbackExtensions.length == 0 || (fallbackExtensions.length == 1 && fallbackExtensions[0] == null) ? list : Arrays.asList(fallbackExtensions)) : list;
	}
	
	/** Returns the associated file extension for the provided MIME type.<br>
	 * If there is no associated extension for the MIME type, but the MIME type contains a three to four character extension code, then that
	 * is returned.<br>
	 * Otherwise, if no extension could be determined, the provided fallback extension is returned.
	 *
	 * @param mimeType The MIME type
	 * @param fallbackExtension A fallback extension that will be returned in the event no extension could be determined
	 * @return The MIME type's extension (with leading dot) */
	public static final String getFirstExtensionForMimeType(String mimeType, String fallbackExtension) {
		List<String> extensions = getExtensionsForMimeType(mimeType, fallbackExtension);
		if(extensions.isEmpty()) {
			if(mimeType != null && mimeType.indexOf('/') > 0 && mimeType.indexOf('/') < mimeType.length() - 1) {
				mimeType = mimeType.substring(mimeType.indexOf('/') + 1);
				mimeType = mimeType.contains("-") ? mimeType.substring(mimeType.lastIndexOf('-') + 1) : mimeType;
				if(mimeType.length() >= 3 && mimeType.length() <= 4) {
					return ".".concat(mimeType);
				}
			}
			return fallbackExtension;
		}
		for(String ext : extensions) {
			if(!ext.contains("-")) {
				return ext;
			}
		}
		String ext = extensions.get(0);
		return ext.contains("-") ? ext.substring(ext.lastIndexOf('-' + 1)) : ext;
	}
	
	/** Identical to {@link #getMimeTypeForExtension(String)}, except that
	 * certain file extensions will return different MIME types for
	 * <tt>VLC Media Player</tt>
	 *
	 * @param ext The file extension
	 * @return The resulting mime type, or the {@link #DEFAULT_MIME_TYPE} if the
	 *         given extension did not have an associated MIME type */
	public static final String getVLC_MimeTypeForExtension(String ext) {
		if(ext == null) {
			return null;
		}
		if(!ext.startsWith(".")) {
			ext = "." + ext;
		}
		String mimeType = null;
		for(Entry<String, String> entry : VLC_MIME_Types.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(ext)) {
				mimeType = entry.getValue();
				break;
			}
		}
		return mimeType == null ? getMimeTypeForExtension(ext) : mimeType;
	}
	
	public static final String getMimeType(String ext, String userAgent) {
		if(userAgent != null && userAgent.contains("VLC/")) {
			return MimeTypes.getVLC_MimeTypeForExtension(ext);
		}
		return MimeTypes.getMimeTypeForExtension(ext);
	}
	
	public static final String getMimeType(File file, String userAgent) {
		if(file.isDirectory()) {
			return "Directory";
		}
		return MimeTypes.getMimeType(getFileExtension(file.getName()), userAgent);
	}
	
	public static final String getMimeType(File file) {
		return MimeTypes.getMimeType(file, null);
	}
	
	public static final boolean isZipFile(String ext) {
		ext = ext.startsWith(".") ? ext.substring(1) : ext;
		switch(ext) {
		case "$7z":// application/x-7z-compressed
		case "7z":// application/x-7z-compressed
		case "ace":// application/x-ace-compressed
		case "cab":// application/vnd.ms-cab-compressed
		case "gz":// application/x-gzip
		case "jar":// application/java-archive
		case "tar":// application/x-tar
		case "themepack":// application/zip
		case "rar":// application/x-rar-compressed
		case "z":// application/x-compressed
		case "zip":// application/zip
			return true;
		default:
			return false;
		}
	}
	
	public static final boolean doesZipOpenWith7Zip(String ext) throws IllegalArgumentException {
		ext = ext.startsWith(".") ? ext.substring(1) : ext;
		switch(ext) {
		case "$7z":// application/x-7z-compressed
		case "7z":// application/x-7z-compressed
		case "ace":// application/x-ace-compressed
		case "cab":// application/vnd.ms-cab-compressed
		case "gz":// application/x-gzip
		case "tar":// application/x-tar
		case "rar":// application/x-rar-compressed
		case "z":// application/x-compressed
			return true;
		case "jar":// application/java-archive
		case "themepack":// application/zip
		case "zip":// application/zip
			return false;
		default:
			throw new IllegalArgumentException(String.format("Extension \"%s\" is not a recognized zip file type!", ext));
		}
	}
	
	public static final boolean isZipGZipped(String ext) throws IllegalArgumentException {
		if((ext.contains(".") ? ext.substring(ext.lastIndexOf('.')) : ext).equalsIgnoreCase("gz")) {// e.g. *.tar.gz, *.log.gz, etc.
			return true;
		}
		ext = ext.startsWith(".") ? ext.substring(1) : ext;
		switch(ext) {
		case "$7z":// application/x-7z-compressed
		case "7z":// application/x-7z-compressed
		case "ace":// application/x-ace-compressed
		case "cab":// application/vnd.ms-cab-compressed
		case "jar":// application/java-archive
		case "tar":// application/x-tar
		case "themepack":// application/zip
		case "rar":// application/x-rar-compressed
		case "z":// application/x-compressed
		case "zip":// application/zip
			return false;
		case "gz":// application/x-gzip");
			return true;
		default:
			throw new IllegalArgumentException(String.format("Extension \"%s\" is not a recognized zip file type!", ext));
		}
	}
	
}

package net.iyouyu.schedule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Created by jesse on 2015/5/8.
 */
public  class md5{



    public static String code1 = "\tfunction chkpwd(uid,pw)\n" +
            "\t{\n" +
            "\t\tvar schoolcode=\"10611\";              \n" +
            "\t\tvar s=md5(uid+md5(pw).substring(0,30).toUpperCase()+schoolcode).substring(0,30).toUpperCase();\n" +
            "\t\treturn s;                \n" +
            "\t}\n" +
            "\t\t\t\t\n" +
            "\tfunction md5js(pass, code, uin) {\n" +
            "\tvar I = hexchar2bin(md5(pass));\n" +
            "\tvar H = md5(I + uin);\n" +
            "\tvar G = md5(H + code.toUpperCase());\n" +
            "\treturn G\n" +
            "\t}\n" +
            "\tvar hexcase = 1;\n" +
            "\tvar b64pad = \"\";\n" +
            "\tvar chrsz = 8;\n" +
            "\tvar mode = 32;\n" +
            "\tfunction md5(A) {\n" +
            "\treturn hex_md5(A)\n" +
            "\t}\n" +
            "\tfunction hex_md5(A) {\n" +
            "\treturn binl2hex(core_md5(str2binl(A), A.length * chrsz))\n" +
            "\t}\n" +
            "\tfunction str_md5(A) {\n" +
            "\treturn binl2str(core_md5(str2binl(A), A.length * chrsz))\n" +
            "\t}\n" +
            "\tfunction hex_hmac_md5(A, B) {\n" +
            "\treturn binl2hex(core_hmac_md5(A, B))\n" +
            "\t}\n" +
            "\tfunction b64_hmac_md5(A, B) {\n" +
            "\treturn binl2b64(core_hmac_md5(A, B))\n" +
            "\t}\n" +
            "\tfunction str_hmac_md5(A, B) {\n" +
            "\treturn binl2str(core_hmac_md5(A, B))\n" +
            "\t}\n" +
            "\tfunction core_md5(K, F) {\n" +
            "\tK[F >> 5] |= 128 << ((F) % 32);\n" +
            "\tK[(((F + 64) >>> 9) << 4) + 14] = F;\n" +
            "\tvar J = 1732584193;\n" +
            "\tvar I = -271733879;\n" +
            "\tvar H = -1732584194;\n" +
            "\tvar G = 271733878;\n" +
            "\tfor (var C = 0; C < K.length; C += 16) {\n" +
            "\t\tvar E = J;\n" +
            "\t\tvar D = I;\n" +
            "\t\tvar B = H;\n" +
            "\t\tvar A = G;\n" +
            "\t\tJ = md5_ff(J, I, H, G, K[C + 0], 7, -680876936);\n" +
            "\t\tG = md5_ff(G, J, I, H, K[C + 1], 12, -389564586);\n" +
            "\t\tH = md5_ff(H, G, J, I, K[C + 2], 17, 606105819);\n" +
            "\t\tI = md5_ff(I, H, G, J, K[C + 3], 22, -1044525330);\n" +
            "\t\tJ = md5_ff(J, I, H, G, K[C + 4], 7, -176418897);\n" +
            "\t\tG = md5_ff(G, J, I, H, K[C + 5], 12, 1200080426);\n" +
            "\t\tH = md5_ff(H, G, J, I, K[C + 6], 17, -1473231341);\n" +
            "\t\tI = md5_ff(I, H, G, J, K[C + 7], 22, -45705983);\n" +
            "\t\tJ = md5_ff(J, I, H, G, K[C + 8], 7, 1770035416);\n" +
            "\t\tG = md5_ff(G, J, I, H, K[C + 9], 12, -1958414417);\n" +
            "\t\tH = md5_ff(H, G, J, I, K[C + 10], 17, -42063);\n" +
            "\t\tI = md5_ff(I, H, G, J, K[C + 11], 22, -1990404162);\n" +
            "\t\tJ = md5_ff(J, I, H, G, K[C + 12], 7, 1804603682);\n" +
            "\t\tG = md5_ff(G, J, I, H, K[C + 13], 12, -40341101);\n" +
            "\t\tH = md5_ff(H, G, J, I, K[C + 14], 17, -1502002290);\n" +
            "\t\tI = md5_ff(I, H, G, J, K[C + 15], 22, 1236535329);\n" +
            "\t\tJ = md5_gg(J, I, H, G, K[C + 1], 5, -165796510);\n" +
            "\t\tG = md5_gg(G, J, I, H, K[C + 6], 9, -1069501632);\n" +
            "\t\tH = md5_gg(H, G, J, I, K[C + 11], 14, 643717713);\n" +
            "\t\tI = md5_gg(I, H, G, J, K[C + 0], 20, -373897302);\n" +
            "\t\tJ = md5_gg(J, I, H, G, K[C + 5], 5, -701558691);\n" +
            "\t\tG = md5_gg(G, J, I, H, K[C + 10], 9, 38016083);\n" +
            "\t\tH = md5_gg(H, G, J, I, K[C + 15], 14, -660478335);\n" +
            "\t\tI = md5_gg(I, H, G, J, K[C + 4], 20, -405537848);\n" +
            "\t\tJ = md5_gg(J, I, H, G, K[C + 9], 5, 568446438);\n" +
            "\t\tG = md5_gg(G, J, I, H, K[C + 14], 9, -1019803690);\n" +
            "\t\tH = md5_gg(H, G, J, I, K[C + 3], 14, -187363961);\n" +
            "\t\tI = md5_gg(I, H, G, J, K[C + 8], 20, 1163531501);\n" +
            "\t\tJ = md5_gg(J, I, H, G, K[C + 13], 5, -1444681467);\n" +
            "\t\tG = md5_gg(G, J, I, H, K[C + 2], 9, -51403784);\n" +
            "\t\tH = md5_gg(H, G, J, I, K[C + 7], 14, 1735328473);\n" +
            "\t\tI = md5_gg(I, H, G, J, K[C + 12], 20, -1926607734);\n" +
            "\t\tJ = md5_hh(J, I, H, G, K[C + 5], 4, -378558);\n" +
            "\t\tG = md5_hh(G, J, I, H, K[C + 8], 11, -2022574463);\n" +
            "\t\tH = md5_hh(H, G, J, I, K[C + 11], 16, 1839030562);\n" +
            "\t\tI = md5_hh(I, H, G, J, K[C + 14], 23, -35309556);\n" +
            "\t\tJ = md5_hh(J, I, H, G, K[C + 1], 4, -1530992060);\n" +
            "\t\tG = md5_hh(G, J, I, H, K[C + 4], 11, 1272893353);\n" +
            "\t\tH = md5_hh(H, G, J, I, K[C + 7], 16, -155497632);\n" +
            "\t\tI = md5_hh(I, H, G, J, K[C + 10], 23, -1094730640);\n" +
            "\t\tJ = md5_hh(J, I, H, G, K[C + 13], 4, 681279174);\n" +
            "\t\tG = md5_hh(G, J, I, H, K[C + 0], 11, -358537222);\n" +
            "\t\tH = md5_hh(H, G, J, I, K[C + 3], 16, -722521979);\n" +
            "\t\tI = md5_hh(I, H, G, J, K[C + 6], 23, 76029189);\n" +
            "\t\tJ = md5_hh(J, I, H, G, K[C + 9], 4, -640364487);\n" +
            "\t\tG = md5_hh(G, J, I, H, K[C + 12], 11, -421815835);\n" +
            "\t\tH = md5_hh(H, G, J, I, K[C + 15], 16, 530742520);\n" +
            "\t\tI = md5_hh(I, H, G, J, K[C + 2], 23, -995338651);\n" +
            "\t\tJ = md5_ii(J, I, H, G, K[C + 0], 6, -198630844);\n" +
            "\t\tG = md5_ii(G, J, I, H, K[C + 7], 10, 1126891415);\n" +
            "\t\tH = md5_ii(H, G, J, I, K[C + 14], 15, -1416354905);\n" +
            "\t\tI = md5_ii(I, H, G, J, K[C + 5], 21, -57434055);\n" +
            "\t\tJ = md5_ii(J, I, H, G, K[C + 12], 6, 1700485571);\n" +
            "\t\tG = md5_ii(G, J, I, H, K[C + 3], 10, -1894986606);\n" +
            "\t\tH = md5_ii(H, G, J, I, K[C + 10], 15, -1051523);\n" +
            "\t\tI = md5_ii(I, H, G, J, K[C + 1], 21, -2054922799);\n" +
            "\t\tJ = md5_ii(J, I, H, G, K[C + 8], 6, 1873313359);\n" +
            "\t\tG = md5_ii(G, J, I, H, K[C + 15], 10, -30611744);\n" +
            "\t\tH = md5_ii(H, G, J, I, K[C + 6], 15, -1560198380);\n" +
            "\t\tI = md5_ii(I, H, G, J, K[C + 13], 21, 1309151649);\n" +
            "\t\tJ = md5_ii(J, I, H, G, K[C + 4], 6, -145523070);\n" +
            "\t\tG = md5_ii(G, J, I, H, K[C + 11], 10, -1120210379);\n" +
            "\t\tH = md5_ii(H, G, J, I, K[C + 2], 15, 718787259);\n" +
            "\t\tI = md5_ii(I, H, G, J, K[C + 9], 21, -343485551);\n" +
            "\t\tJ = safe_add(J, E);\n" +
            "\t\tI = safe_add(I, D);\n" +
            "\t\tH = safe_add(H, B);\n" +
            "\t\tG = safe_add(G, A)\n" +
            "\t}\n" +
            "\tif (mode == 16) {\n" +
            "\t\treturn Array(I, H)\n" +
            "\t} else {\n" +
            "\t\treturn Array(J, I, H, G)\n" +
            "\t}\n" +
            "\t \n" +
            "\t}\n" +
            "\tfunction md5_cmn(F, C, B, A, E, D) {\n" +
            "\treturn safe_add(bit_rol(safe_add(safe_add(C, F), safe_add(A, D)), E), B)\n" +
            "\t}\n" +
            "\tfunction md5_ff(C, B, G, F, A, E, D) {\n" +
            "\treturn md5_cmn((B & G) | ((~B) & F), C, B, A, E, D)\n" +
            "\t}\n" +
            "\tfunction md5_gg(C, B, G, F, A, E, D) {\n" +
            "\treturn md5_cmn((B & F) | (G & (~F)), C, B, A, E, D)\n" +
            "\t}\n" +
            "\tfunction md5_hh(C, B, G, F, A, E, D) {\n" +
            "\treturn md5_cmn(B ^ G ^ F, C, B, A, E, D)\n" +
            "\t}\n" +
            "\tfunction md5_ii(C, B, G, F, A, E, D) {\n" +
            "\treturn md5_cmn(G ^ (B | (~F)), C, B, A, E, D)\n" +
            "\t}\n" +
            "\tfunction core_hmac_md5(C, F) {\n" +
            "\tvar E = str2binl(C);\n" +
            "\tif (E.length > 16) {\n" +
            "\t\tE = core_md5(E, C.length * chrsz)\n" +
            "\t}\n" +
            "\tvar A = Array(16),\n" +
            "\tD = Array(16);\n" +
            "\tfor (var B = 0; B < 16; B++) {\n" +
            "\t\tA[B] = E[B] ^ 909522486;\n" +
            "\t\tD[B] = E[B] ^ 1549556828\n" +
            "\t}\n" +
            "\tvar G = core_md5(A.concat(str2binl(F)), 512 + F.length * chrsz);\n" +
            "\treturn core_md5(D.concat(G), 512 + 128)\n" +
            "\t}\n" +
            "\tfunction safe_add(A, D) {\n" +
            "\tvar C = (A & 65535) + (D & 65535);\n" +
            "\tvar B = (A >> 16) + (D >> 16) + (C >> 16);\n" +
            "\treturn (B << 16) | (C & 65535)\n" +
            "\t}\n" +
            "\tfunction bit_rol(A, B) {\n" +
            "\treturn (A << B) | (A >>> (32 - B))\n" +
            "\t}\n" +
            "\tfunction str2binl(D) {\n" +
            "\tvar C = Array();\n" +
            "\tvar A = (1 << chrsz) - 1;\n" +
            "\tfor (var B = 0; B < D.length * chrsz; B += chrsz) {\n" +
            "\t\tC[B >> 5] |= (D.charCodeAt(B / chrsz) & A) << (B % 32)\n" +
            "\t}\n" +
            "\treturn C\n" +
            "\t}\n" +
            "\tfunction binl2str(C) {\n" +
            "\tvar D = \"\";\n" +
            "\tvar A = (1 << chrsz) - 1;\n" +
            "\tfor (var B = 0; B < C.length * 32; B += chrsz) {\n" +
            "\t\tD += String.fromCharCode((C[B >> 5] >>> (B % 32)) & A)\n" +
            "\t}\n" +
            "\treturn D\n" +
            "\t}\n" +
            "\tfunction binl2hex(C) {\n" +
            "\tvar B = hexcase ? \"0123456789ABCDEF\": \"0123456789abcdef\";\n" +
            "\tvar D = \"\";\n" +
            "\tfor (var A = 0; A < C.length * 4; A++) {\n" +
            "\t\tD += B.charAt((C[A >> 2] >> ((A % 4) * 8 + 4)) & 15) + B.charAt((C[A >> 2] >> ((A % 4) * 8)) & 15)\n" +
            "\t}\n" +
            "\treturn D\n" +
            "\t}\n" +
            "\tfunction binl2b64(D) {\n" +
            "\tvar C = \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\";\n" +
            "\tvar F = \"\";\n" +
            "\tfor (var B = 0; B < D.length * 4; B += 3) {\n" +
            "\t\tvar E = (((D[B >> 2] >> 8 * (B % 4)) & 255) << 16) | (((D[B + 1 >> 2] >> 8 * ((B + 1) % 4)) & 255) << 8) | ((D[B + 2 >> 2] >> 8 * ((B + 2) % 4)) & 255);\n" +
            "\t\tfor (var A = 0; A < 4; A++) {\n" +
            "\t\t\tif (B * 8 + A * 6 > D.length * 32) {\n" +
            "\t\t\t\tF += b64pad\n" +
            "\t\t\t} else {\n" +
            "\t\t\t\tF += C.charAt((E >> 6 * (3 - A)) & 63)\n" +
            "\t\t\t}\n" +
            "\t \n" +
            "\t\t}\n" +
            "\t \n" +
            "\t}\n" +
            "\treturn F\n" +
            "\t}\n" +
            "\tfunction hexchar2bin(str) {\n" +
            "\tvar arr = [];\n" +
            "\tfor (var i = 0; i < str.length; i = i + 2) {\n" +
            "\t\tarr.push(\"\\\\x\" + str.substr(i, 2))\n" +
            "\t}\n" +
            "\tarr = arr.join(\"\");\n" +
            "\teval(\"var temp = '\" + arr + \"'\");\n" +
            "\treturn temp\n" +
            "\t}\n" +
            "\t\n" +
            "\tvar sFile = \"testfile.txt\";\n" +
            "\t\tvar fso = new ActiveXObject(\"Scripting.FileSystemObject\"); \n" +
            "\t\tvar f = fso.OpenTextFile(sFile,2,true); \n" +
            "\t\talert(chkpwd(\"20115351\",\"04116383\"));\n" +
            "\t\tf.writeLine(chkpwd(\"20115351\",\"04116383\")); \n" +
            "\t\tf.close();\n" +
            "\t\t\t" +
            "function setValuesJson(uid,pw)\n" +
            "        {\n" +
            "\t\t\tvar key = chkpwd(uid,pw);\n" +
            "            var result = key;\n" +
            "            window.stub.jsMethod(result);//用接口stub, 通过调用内部类中的方法jsMethod给java传回result。\n" +
            "        }";
}

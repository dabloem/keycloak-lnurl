<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "title">
        ${msg("loginTitle",(realm.displayName!''))}
    <#elseif section = "header">
        ${msg("loginTitleHtml",(realm.displayNameHtml!''))?no_esc}
    <#elseif section = "form">
        <#if realm.password>

            <div id="content">
                <div class="wrap">
                    <h1>Login with lnurl-auth</h1>
                    <p>Scan the QR code to login</p>
                    <a id="qrcode">
                        <img src="${lnurlimg}">
                    </a>

                    <form id="kc-form-login" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post" />

                    <div id="footer">
                        <div class="about">
                            <p>This page uses the lnurl-auth protocol.</p>
                            <p>See <a href="https://github.com/fiatjaf/awesome-lnurl#wallets">list of apps</a> that support it.</p>
                        </div>
                        <div class="help">
                            <p><a href="https://github.com/chill117/passport-lnurl-auth/issues">Report a problem</a></p>
                        </div>
                        <h3 style="display: none">${url.loginAction}</h3>
                    </div>
                </div>
            </div>

            <script type="text/javascript">
                var url = '${kcurl}'.replaceAll('amp;', '');

                var refreshIntervalId = setInterval(function(){
                    fetch(url) // Any output from the script will go to the "result" div
                    .then(response => {
                        if (response.status == 200) {
                            clearInterval(refreshIntervalId);
                            document.getElementById("kc-form-login").submit();
                        } return;
                    })}, 3000);
            </script>
        </#if>
    </#if>
</@layout.registrationLayout>

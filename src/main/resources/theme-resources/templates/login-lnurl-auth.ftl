<#import "template.ftl" as layout>
<@layout.registrationLayout showAnotherWayIfPresent=true ;section>
    <#if section = "title">
        ${msg("loginTitle",(realm.displayName!''))}
    <#elseif section = "header">
        ${msg("loginTitleHtml",(realm.displayNameHtml!''))?no_esc}
    <#elseif section = "form">
        <#if realm.password>
        <div class="login">
            <h1 class="h3">LNURL login</h1>
            <div id="content">
                <div class="wrap">
                    <p>Scan the QR code to login</p>
                    <a id="qrcode" target="_blank" href="lightning:${lnurlAuth}">
                        <img src="${qr}">
                    </a>

                    <#if realm.rememberMe>
                    <p style="padding-top: 10px;">
                        <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                    <p>
                    </#if>

                    <form id="kc-form-login" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
                        <input id="k1" type="hidden" name="k1" value="${k1}">
                    </form>

                    <#if false >
                    <div>
                        <div class="about">
                            <p>This page uses the lnurl-auth protocol.</p>
                            <p>See <a href="https://github.com/fiatjaf/awesome-lnurl#wallets">list of apps</a> that support it.</p>
                        </div>
                    </div>
                    </#if>
                </div>
            </div>
        </div>

        <script type="text/javascript">
                var url = '${pollingUrl}'.replaceAll('amp;', '');

                var refreshIntervalId = setInterval(function(){
                    fetch(url) // Any output from the script will go to the "result" div
                    .then( (response) => {
                            if (response.ok) {
                                clearInterval(refreshIntervalId);
                                console.log("POSTing kc-form-login");
                                document.getElementById("kc-form-login").submit();
                            }
                        }
                    )
                }, 3000);
            </script>
        </#if>
    </#if>
</@layout.registrationLayout>

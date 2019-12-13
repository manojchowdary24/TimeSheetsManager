<#setting locale="en_US">
<!DOCTYPE html>
<html lang="en">
<body>
<table border="0" cellspacing="0" cellpadding="0" id="m_3062005906015095314message-holder" style="font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;border-radius:10px;background-color:#ffffff;padding:0;border:2px solid #ebebeb" bgcolor="#FFFFFF">
  <tbody><tr>
    <td style="font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;font-size:13px;line-height:18px;color:#333333">
      <table border="0" cellspacing="0" cellpadding="12">
        <tbody><tr>
          <td style="font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;font-size:13px;line-height:18px;color:#333333">
            <p id="m_3062005906015095314message-header" style="color:#3f51b5;font-size:18px;text-transform:capitalize;line-height:1.1em;margin:0;padding:0 0 0 5px">
              <span>A user password reset has been requested</span>
            </p>
          </td>
        </tr>
        </tbody></table>
    </td>
  </tr>
  <tr>
    <td class="m_3062005906015095314header-sep" height="2" style="font-size:13px;line-height:18px;font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;color:#333333;height:2px;background-color:#3f51b5" bgcolor="#3f51b5"></td>
  </tr>
  <tr>
    <td style="font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;font-size:13px;line-height:18px;color:#333333">
      <table border="0" cellspacing="0" cellpadding="16">
        <tbody><tr>
          <td class="m_3062005906015095314main-message" style="font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;font-size:1em;line-height:18px;color:#333333">

            <p>
              User Name: ${user.username}
            </p>
            <p>
              Here is your temporary token:
            </p><br>
            <p><font style="font-size:2em;font-weight:bold">${token}</font></p><br>
            <p>
              Please note that temporary tokens are valid for ${expirationDays} days.<br> <br>
              Once you have successfully signed in, the system will ask you to change your password. Follow the instructions on the sign in page. <br><br>
              Application URL: <a href=${appUrl}  style="color:#55a7d8;text-decoration:none;border-bottom-width:1px;border-bottom-color:#55a7d8;border-bottom-style:dotted" target="_blank" data-saferedirecturl="https://www.google.com/url?q=https://www.shopdisney.com&amp;source=gmail&amp;ust=1544807368375000&amp;usg=AFQjCNH5vDSb2aCjO972bG_3Glm3rHZZZg">${appUrl} </a>
            </p>
            <p>If you did not request a password reset, please contact your Administration.</p><br>
            <p>Sincerely,<br><br>Timesheets-Management</p>
          </td>
        </tr>
        </tbody></table>
    </td>
  </tr>
  <tr>
    <td class="m_3062005906015095314header-sep" height="2" style="font-size:13px;line-height:18px;font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;color:#333333;height:2px;background-color:#3f51b5" bgcolor="#3f51b5"></td>
  </tr>
  <tr>
    <td class="m_3062005906015095314supporting-info" style="font-size:13px;font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;line-height:18px;color:#333333;border-bottom-color:#d9d9d9;border-bottom-width:1px;border-bottom-style:solid;background-color:#ebf5fa" bgcolor="#EBF5FA">
      <table border="0" cellspacing="0" cellpadding="18">
        <tbody><tr>
          <td style="font-family:matterhorn,'Helvetica Neue',Helvetica,Arial,sans-serif;font-size:13px;line-height:18px;color:#333333">
            <p style="color:#3f51b5;margin:0">
              <strong><span>Have questions?</span></strong>
            </p>
            <p>
              Please contact your representative.
            </p>&nbsp;
          </td>
        </tr>
        </tbody></table>
    </td>
  </tr>
  </tbody></table>
</body>
</html>
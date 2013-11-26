<%-- 
    Document   : HomePage
    Created on : 26-Nov-2013, 15:42:27
    Author     : Tom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TC Bike Shop</title>
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
    </head>
    <body> 
        <h1 class="heading">TC Bike Shop</h1>

        <table class="box-right">

            <tr>
                <td>
                    <h1 class="h1">Personal Information</h1>
                </td>
            <tr>
                <td>
                    First Name
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Second Name
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Address line 1
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Address line 2
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>    

            <tr>
                <td>
                    Address line 3
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Postcode
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Email Address
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>           

            <tr>
                <td>
                    <h1 class="h1">Card Details</h1>
                </td>

            <tr>
                <td>
                    Type of Card
                </td>
            <tr>
                <td>
                    <select> 
                        <option value="0">Vista</option>             
                        <option value="0">Debit</option>                 
                        <option value="0">American Express</option>
                        <option value="0">Electro</option>
                        <option value="0">Master Card</option>
                    </select>
                </td>

            <tr>
                <td>
                    Name on Card
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Card Number
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Security Code
                </td>
                <td>
                    <input type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    Expiry Date
                </td>
            <tr>
                <td>
                    <select> 
                        <option value="0">0</option>
                        <option value="0">1</option>
                        <option value="0">2</option>
                        <option value="0">3</option>
                        <option value="0">4</option>
                        <option value="0">5</option>
                        <option value="0">6</option>
                        <option value="0">7</option>
                        <option value="0">8</option>
                        <option value="0">9</option>
                        <option value="0">10</option> 
                        <option value="0">11</option>
                        <option value="0">12</option>

                    </select>
                </td>

            <tr>
                <td>
                    <select> 
                        <option value="0">0000</option>
                        <option value="0">2013</option>
                        <option value="0">2014</option>
                        <option value="0">2015</option>
                        <option value="0">2016</option>
                        <option value="0">2017</option>
                        <option value="0">2018</option>
                        <option value="0">2019</option>
                        <option value="0">2020</option>
                        <option value="0">2021</option>
                        <option value="0">2022</option>                       

                    </select>
                </td>

        </table>
        <table class="box-right">

            <h1 class="h1">Bike types</h1>
            Number of Bikes
            <tr>
            <input type="text"/> 
            <br>        
            <input type="radio" name="sex" value="male">Morning
            <input type="radio" name="sex" value="female">Afternoon (After 12pm)
            </form>    
            <br>
            <input type="radio" name="sex" value="male"> Men’s Hybrid.
            <input type="radio" name="sex" value="male"> Women's Hybrid.         
            </tr>
            <br>
            <input type="radio" name="sex" value="male"> Men’s All-terrain.
            <input type="radio" name="sex" value="male"> Women's All-terrain. 
            <br>
            <input type="radio" name="sex" value="male"> Tandem
            <br>
            <input type="radio" name="sex" value="male"> Child’s All-terrain
        </table> 

        <br>

        <input type="Submit"/> 




    </tbody>

</form>

</body>
</html>

# Welcome

Thanks for using **Bundle Calculate Application**!!
## Usage background
When a product is bundle sold, this calculator can help buyers determine the cheapest cost and bundle breakdown according to the type of bundle sale.

For example:

A type of product is sold as following:

3 @ $427.50 6 @ $810 9 @ $1147.50

If a customer want to buy 15 unit, he should buy 1 * 9 ($1147.50) and 1 * 6 ($810) for the cheapest cost of $1957.50.

## How to use this Application

### Step1:

### Modify `order.txt` and `bundleTableConfig.txt` in the `src/main/resources`.

**`order.txt`** is the input for each order.

An example `order.txt`:
```
10 IMG
15 FLAC
13 VID
```
***`order.txt` format (for each line)***:

`Integer`(the number of product need to purchase) + `Product Code`.
hint: each element should be separated by *ONLY ONE* space!! The application *will not* calculate the line with wrong format!!

**`bundleTableConfig.txt`** is the bundles configuration that product sold.

For example:

The following is the information of products 

Product Name | Product Code | Bundles
----------------- | ----------- | -------
Image | IMG | 5 @ $450 10 @ $800
Audio | Flac | 3 @ $427.50 6 @ $810 9 @ $1147.50
Video | VID | 3 @ $570 5 @ $900 9 @ $1530

The corresponding `bundleTableConfig.txt`:
```
IMG 5:450 10:800
FLAC 3:427.50 6:810 9:1147.50
VID 3:570 5:900 9:1530
```

***`bundleTableConfig.txt` format (for each line)***:

`Product Code` + `bunle number:bundle price` + `bunle number:bundle price` + ...

hint: each element should be separated by *ONLY ONE* space!! The application will not config the line with wrong format!!

### Step 2:
### Run `BundleCalculateApp.java` in the `src/main/java`.

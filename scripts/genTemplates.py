import os

textureDir = "../src/main/resources/assets/chisel/textures/block/gold"
setName = "extra_gold"

def main():
	result = ""
	fields = []
	for root, dirs, files in os.walk(textureDir):
		for f in files:
			if f.endswith(".png"):
				name = f[:-4]
				if name.endswith("ctm") or name.endswith("ctmh") or name.endswith("ctmv"):
					continue
				field = name.upper()
				fields.append(field)
				result += "public static final VariantTemplate %s = create(\"%s\");\n" % (field, name)
	result += "public static final VariantTemplate[] ARRAY_%s = new VariantTemplate[] {%s};\n" % (setName.upper(), ", ".join(fields))

	print(result)

if __name__ == "__main__":
	main()
